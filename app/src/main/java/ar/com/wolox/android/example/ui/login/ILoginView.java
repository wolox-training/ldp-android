package ar.com.wolox.android.example.ui.login;

import android.support.annotation.NonNull;

/**
 * The View in the MVP architectural pattern for the Login feature
 */
public interface ILoginView {

    void showEmptyFormErrorOnEmail();
    void showInvalidEmailError();
    void setEmail(@NonNull String email);
    void setPassword(@NonNull String password);
    void onUserLoggedIn();
    void onWrongEmailOrPassword();
    void showProgress();
    void hideProgress();
    void showNoConnectionError();
    void showUnexpectedError();
    void showEmptyFormErrorOnPassword();
}
