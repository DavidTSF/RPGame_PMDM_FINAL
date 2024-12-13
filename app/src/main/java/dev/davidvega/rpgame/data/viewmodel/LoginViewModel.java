package dev.davidvega.rpgame.data.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import dev.davidvega.rpgame.data.model.User;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import dev.davidvega.rpgame.login.Clase;
import dev.davidvega.rpgame.net.api.ApiResponse;
import dev.davidvega.rpgame.net.api.LoginApiService;
import dev.davidvega.rpgame.net.api.PlayerDataPrimitive;
import dev.davidvega.rpgame.net.api.RPGApiService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The type Login view model.
 */
public class LoginViewModel extends AndroidViewModel {
    /**
     * The Executor.
     */
    Executor executor;
    /**
     * The Retrofit.
     */
    Retrofit retrofit;
    /**
     * The Service.
     */
    LoginApiService service;
    /**
     * The Login model.
     */
    LoginModel loginModel;

    /**
     * The Current user.
     */
    MutableLiveData<UserStatus> currentUser = new MutableLiveData<>();
    /**
     * The Pass to game.
     */
    MutableLiveData<Boolean> passToGame = new MutableLiveData<>(false);
    /**
     * The Has died.
     */
    MutableLiveData<Boolean> hasDied = new MutableLiveData<>(false);

    /**
     * The type Raw user.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class RawUser {
        /**
         * The Exists.
         */
        boolean exists;
        /**
         * The Data.
         */
        Data data;

        /**
         * The type Data.
         */
        @NoArgsConstructor
        @AllArgsConstructor
        @Setter
        @Getter
        public static class Data {
            /**
             * The Username.
             */
            String username;
            /**
             * The Playerdata.
             */
            String playerdata;
        }
    }

    /**
     * The type User status.
     */
    public static class UserStatus {
        /**
         * The Logged in.
         */
        public boolean loggedIn;
        /**
         * The User.
         */
        public User user;
        /**
         * The Has to create character.
         */
        public boolean hasToCreateCharacter;

        /**
         * Instantiates a new User status.
         *
         * @param loggedIn             the logged in
         * @param user                 the user
         * @param hasToCreateCharacter the has to create character
         */
        public UserStatus(boolean loggedIn, User user, boolean hasToCreateCharacter) {
            this.loggedIn = loggedIn;
            this.user = user;
            this.hasToCreateCharacter = hasToCreateCharacter;
        }
    }

    /**
     * Instantiates a new Login view model.
     *
     * @param application the application
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        retrofit = new Retrofit.Builder()
                .baseUrl(RPGApiService.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        service = retrofit.create(LoginApiService.class);
        loginModel = new LoginModel();
    }

    /**
     * User login.
     *
     * @param username the username
     */
    public void userLogin( String username) {
        Call<RawUser> userLogin = service.getUserData(username);
        // Al tratar de logearse
        userLogin.enqueue(new Callback<RawUser>() {
            @Override
            public void onResponse(Call<RawUser> call, Response<RawUser> response) {
                // Si el server dice que existe
                if ( response.body().exists ) {
                    RawUser.Data rawUser = response.body().data;
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        PlayerDataPrimitive playerDataPrimitive = objectMapper.readValue(rawUser.playerdata, PlayerDataPrimitive.class);

                        Log.d("DEBUG_LOGIN_PRIMITIVE", playerDataPrimitive.getInventory().toString());

                        PlayerCharacter playerCharacter = playerDataPrimitive.toPlayerCharacter();

                        Log.d("DEBUG_LOGIN_PLAYER", playerCharacter.toString());

                        User user = new User(
                                rawUser.username,
                                playerCharacter
                        );

                        currentUser.postValue( new UserStatus(
                                true,
                                user,
                                false
                        ) );
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } else {
                    User user = new User(username);
                    currentUser.postValue( new UserStatus(
                            true,
                            user,
                            true
                    ) );
                }
            }
            @Override
            public void onFailure(Call<RawUser> call, Throwable t) {
                Log.d("Error", "NO SE HA PODIDO ENCONTRAR LA API" + t.toString());
            }
        });
    }

    /**
     * Create character.
     *
     * @param clase    the clase
     * @param charName the char name
     */
    public void createCharacter ( Clase clase, String charName ) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
            }
        });

        User user = getCurrentUser().getValue().user;
        Boolean playerGotKilled = hasDied.getValue();

        Log.d("DEBUG_CHARACTER_CREATOR", playerGotKilled ? "Ha muerto y a vuelto" : "No ha muerto, acaba de llegar");
        Log.d("DEBUG_CHARACTER_CREATOR", user.toString());

        if (user.getPlayerCharacter() == null) {
            user.setPlayerCharacter(PlayerCharacter.baseCharacter(charName));
        }
        if (user.getPlayerCharacter().getPlayerClass() == null) {
            user.getPlayerCharacter().setPlayerClass(clase);
        }

        if ( playerGotKilled ) {
            Log.d("DEBUG_CHARACTER_CREATOR_BEFORE_SETTING", user.toString());
            user.setPlayerCharacter(PlayerCharacter.baseCharacter(charName));
            user.getPlayerCharacter().setPlayerClass(clase);

            Log.d("DEBUG_CHARACTER_CREATOR_AFTER_SETTING", user.toString());
            loginModel.createCharacterWithClass(user, charName, updatedUser -> {
                currentUser.setValue(new UserStatus(true, updatedUser, false));

                updateUserAfterDeath(updatedUser);
            });

        } else {

            user.getPlayerCharacter().setPlayerClass(clase);

            loginModel.createCharacterWithClass(user, charName, updatedUser -> {
                currentUser.setValue(new UserStatus(true, updatedUser, false));
                createUser(updatedUser);
            });

        }
    }

    /**
     * Create user.
     *
     * @param user the user
     */
    public void createUser( User user ) {
        if (user.getPlayerCharacter() == null || user.getPlayerCharacter().getName() == null) {
            Log.d("DEBUG_LOGIN", "No existe los datos de usuario, de mirar...");
            return;
        }

        Log.d("DEBUG_LOGIN", "User name:"+ user.getUsername() );
        Log.d("DEBUG_LOGIN", "User char name:"+ user.getPlayerdataLiveData().getValue().getName() );

        PlayerDataPrimitive playerDataPrimitive = new PlayerDataPrimitive(user.getPlayerCharacter());

        Call<ApiResponse> createUserCall = service.createUser(user.getUsername(), playerDataPrimitive);
        createUserCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                passToGame.postValue(true);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Error", "NO SE HA CREAR EL USUARIO" + t.toString());
            }
        });
    }

    /**
     * Update user after death.
     *
     * @param user the user
     */
    public void updateUserAfterDeath( User user ) {
        if (user.getPlayerCharacter() == null || user.getPlayerCharacter().getName() == null) {
            Log.d("DEBUG_LOGIN", "No existe los datos de usuario, de mirar...");
            return;
        }

        Log.d("DEBUG_LOGIN", "User name:"+ user.getUsername() );
        Log.d("DEBUG_LOGIN", "User char name:"+ user.getPlayerdataLiveData().getValue().getName() );

        PlayerDataPrimitive playerDataPrimitive = new PlayerDataPrimitive(user.getPlayerCharacter());

        Call<ApiResponse> createUserCall = service.updateUser(user.getUsername(), playerDataPrimitive);
        createUserCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                passToGame.postValue(true);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Error", "NO SE HA CREAR EL USUARIO" + t.toString());
            }
        });
    }

    /**
     * Update user from game.
     *
     * @param user the user
     */
    public void updateUserFromGame( User user ) {
        if (user.getPlayerCharacter() == null || user.getPlayerCharacter().getName() == null) {
            Log.d("DEBUG_LOGIN_UPDATE_FROM_GAME", "No existe los datos de usuario, de mirar...");
            return;
        }

        Log.d("DEBUG_LOGIN", "User name:"+ user.getUsername() );
        Log.d("DEBUG_LOGIN", "User char name:"+ user.getPlayerdataLiveData().getValue().getName() );

        PlayerDataPrimitive playerDataPrimitive = new PlayerDataPrimitive(user.getPlayerCharacter());

        Call<ApiResponse> createUserCall = service.updateUser(user.getUsername(), playerDataPrimitive);
        createUserCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Error", "NO SE HA CREAR EL USUARIO" + t.toString());
            }
        });
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public MutableLiveData<UserStatus> getCurrentUser() {
        return currentUser;
    }

    /**
     * The interface Users callback.
     */
    public interface UsersCallback {
        /**
         * User creado.
         *
         * @param user the user
         */
        void userCreado(User user);
    }

    /**
     * Gets pass to game.
     *
     * @return the pass to game
     */
    public MutableLiveData<Boolean> getPassToGame() {
        return passToGame;
    }

    /**
     * Gets has died.
     *
     * @return the has died
     */
    public MutableLiveData<Boolean> getHasDied() {
        return hasDied;
    }

    /**
     * Sets has died.
     *
     * @param hasDied the has died
     */
    public void setHasDied(MutableLiveData<Boolean> hasDied) {
        this.hasDied = hasDied;
    }
}
