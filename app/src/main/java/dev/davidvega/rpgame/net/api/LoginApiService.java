package dev.davidvega.rpgame.net.api;


import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginApiService {
    String BASE_URL = "https://api.taxistahosting.com/";

    @GET("user/{username}")
    Call<LoginViewModel.RawUser> getUserData(@Path("username") String username);

    @POST("createuser/{username}")
    Call<ApiResponse> createUser(@Path("username") String username, @Body PlayerCharacter playerData);


}
