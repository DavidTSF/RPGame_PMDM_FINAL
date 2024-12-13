package dev.davidvega.rpgame.net.api;


import dev.davidvega.rpgame.data.viewmodel.LoginViewModel;
import dev.davidvega.rpgame.game.model.PlayerCharacter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The interface Login api service.
 */
public interface LoginApiService {
    /**
     * The constant BASE_URL.
     */
    String BASE_URL = "https://api.taxistahosting.com/";

    /**
     * Gets user data.
     *
     * @param username the username
     * @return the user data
     */
    @GET("user/{username}")
    Call<LoginViewModel.RawUser> getUserData(@Path("username") String username);

    /**
     * Create user call.
     *
     * @param username   the username
     * @param playerData the player data
     * @return the call
     */
    @POST("createuser/{username}")
    Call<ApiResponse> createUser(@Path("username") String username, @Body PlayerDataPrimitive playerData);

    /**
     * Update user call.
     *
     * @param username   the username
     * @param playerData the player data
     * @return the call
     */
    @POST("updateuser/{username}")
    Call<ApiResponse> updateUser(@Path("username") String username, @Body PlayerDataPrimitive playerData);


}
