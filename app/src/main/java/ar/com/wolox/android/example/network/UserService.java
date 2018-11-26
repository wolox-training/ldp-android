package ar.com.wolox.android.example.network;

import java.util.List;

import ar.com.wolox.android.example.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API services for user entity;
 */
public interface UserService {

    @GET("/users")
    Call<List<User>> login(@Query("email") String email, @Query("password") String password);
}
