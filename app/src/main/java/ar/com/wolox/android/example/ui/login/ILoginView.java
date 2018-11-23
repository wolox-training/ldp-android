package ar.com.wolox.android.example.ui.login;

/**
 * The View in the MVP architectural pattern for the Login feature
 */
public interface ILoginView {
    void showEmptyFormError();
    void showInvalidEmailError();
    void setEmail(String email);
    void setPassword(String password);
    void onUserSaved();
}
