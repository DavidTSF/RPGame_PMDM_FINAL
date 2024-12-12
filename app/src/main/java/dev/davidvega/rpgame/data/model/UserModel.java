package dev.davidvega.rpgame.data.model;

import android.util.Log;

import dev.davidvega.rpgame.net.api.ApiResponse;
import dev.davidvega.rpgame.net.api.LoginApiService;
import dev.davidvega.rpgame.net.api.PlayerDataPrimitive;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModel {

    public void updateUserFromGame( LoginApiService service, User user ) {
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

}
