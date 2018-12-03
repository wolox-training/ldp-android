package ar.com.wolox.android.example.model;

import android.support.annotation.NonNull;

/**
 * Model for user entity;
 */
public class User {

    private Integer id;
    private String email;
    private String password;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
