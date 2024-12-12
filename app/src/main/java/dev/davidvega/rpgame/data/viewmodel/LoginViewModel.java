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

public class LoginViewModel extends AndroidViewModel {
    Executor executor;
    Retrofit retrofit;
    LoginApiService service;
    LoginModel loginModel;

    MutableLiveData<UserStatus> currentUser = new MutableLiveData<>();
    MutableLiveData<Boolean> passToGame = new MutableLiveData<>(false);
    MutableLiveData<Boolean> hasDied = new MutableLiveData<>(false);

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class RawUser {
        boolean exists;
        Data data;
        @NoArgsConstructor
        @AllArgsConstructor
        @Setter
        @Getter
        public static class Data {
            String username;
            String playerdata;
        }
    }

    public static class UserStatus {
        public boolean loggedIn;
        public User user;
        public boolean hasToCreateCharacter;
        public UserStatus(boolean loggedIn, User user, boolean hasToCreateCharacter) {
            this.loggedIn = loggedIn;
            this.user = user;
            this.hasToCreateCharacter = hasToCreateCharacter;
        }
    }

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

    public MutableLiveData<UserStatus> getCurrentUser() {
        return currentUser;
    }

    public interface UsersCallback {
        void userCreado(User user);
    }

    public MutableLiveData<Boolean> getPassToGame() {
        return passToGame;
    }

    public MutableLiveData<Boolean> getHasDied() {
        return hasDied;
    }

    public void setHasDied(MutableLiveData<Boolean> hasDied) {
        this.hasDied = hasDied;
    }
}
