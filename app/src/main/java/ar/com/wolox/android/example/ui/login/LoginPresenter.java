package ar.com.wolox.android.example.ui.login;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ar.com.wolox.android.example.utils.Constants;
import ar.com.wolox.android.example.utils.StringUtils;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager;

/**
 * The Presenter(P) in the MVP architectural pattern for Login featureclwea
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private SharedPreferencesManager mSharedPreferencesManager;

    @Inject
    public LoginPresenter(SharedPreferencesManager mSharedPreferencesManager) {
        this.mSharedPreferencesManager = mSharedPreferencesManager;
    }

    /**
     * Logs in an user;
     *
     * @param email    Input email
     * @param password Input password
     */
    void login(@NonNull String email, @NonNull String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            runIfViewAttached(() -> getView().showEmptyFormError());
            return;
        }

        if (!StringUtils.isEmail(email)) {
            runIfViewAttached(() -> getView().showInvalidEmailError());
            return;
        }

        mSharedPreferencesManager.store(Constants.UserCredentials.USER_EMAIL, email);
        mSharedPreferencesManager.store(Constants.UserCredentials.USER_PASSWORD, password);
        runIfViewAttached(ILoginView::onUserSaved);
    }

    /**
     * Load saved email & password
     */
    void loadEmailAndPassword() {
        runIfViewAttached((view) -> {
            view.setEmail(mSharedPreferencesManager.get(Constants.UserCredentials.USER_EMAIL, ""));
            view.setPassword(mSharedPreferencesManager.get(Constants.UserCredentials.USER_PASSWORD, ""));
        });
    }
}
