package ar.com.wolox.android.example.model;

import android.support.annotation.NonNull;

/**
 * Model for user entity;
 */
public class User {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
