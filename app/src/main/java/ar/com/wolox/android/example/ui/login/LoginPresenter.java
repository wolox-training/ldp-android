package ar.com.wolox.android.example.ui.login;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import ar.com.wolox.android.example.model.User;
import ar.com.wolox.android.example.network.UserService;
import ar.com.wolox.android.example.utils.Constants;
import ar.com.wolox.android.example.utils.SimpleNetworkCallback;
import ar.com.wolox.android.example.utils.StringUtils;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import okhttp3.ResponseBody;

/**
 * The Presenter(P) in the MVP architectural pattern for Login feature;
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private SharedPreferencesManager mSharedPreferencesManager;
    private UserService mUserService;

    @Inject
    public LoginPresenter(SharedPreferencesManager mSharedPreferencesManager, RetrofitServices mRetrofitServices) {
        this.mSharedPreferencesManager = mSharedPreferencesManager;
        this.mUserService = mRetrofitServices.getService(UserService.class);
    }

    /**
     * Logs in an user;
     *
     * @param email    Input email
     * @param password Input password
     */
    void login(@NonNull String email, @NonNull String password) {
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(password)) {
            runIfViewAttached(ILoginView::showEmptyFormErrorOnEmail);
        } else if (StringUtils.isEmpty(email)) {
            runIfViewAttached(ILoginView::showEmptyFormErrorOnEmail);
        } else if (StringUtils.isEmpty(password)) {
            runIfViewAttached(ILoginView::showEmptyFormErrorOnPassword);
        } else if (!StringUtils.isEmail(email)) {
            runIfViewAttached(() -> getView().showInvalidEmailError());
        } else {

            runIfViewAttached(ILoginView::showProgress);

            mUserService.login(email, password).enqueue(new SimpleNetworkCallback<List<User>>() {
                @Override
                public void onResponseSuccessful(@Nullable List<User> users) {
                    runIfViewAttached(ILoginView::hideProgress);

                    if (users == null || users.size() == 0) {
                        runIfViewAttached(ILoginView::onWrongEmailOrPassword);
                        return;
                    }

                    User user = users.get(0);
                    LoginPresenter.this.storeEmail(user.getEmail());
                    mSharedPreferencesManager.store(Constants.UserCredentials.USER_LOGGED_IN, true);

                    runIfViewAttached(ILoginView::onUserLoggedIn);
                }

                @Override
                public void onResponseFailed(@Nullable ResponseBody responseBody, int statusCode) {
                    super.onResponseFailed(responseBody, statusCode);

                    runIfViewAttached(iLoginView -> {
                        iLoginView.hideProgress();
                        iLoginView.showUnexpectedError();
                    });
                }

                @Override
                public void onCallFailure(@Nullable Throwable exception) {
                    super.onCallFailure(exception);

                    runIfViewAttached(iLoginView -> {
                        iLoginView.hideProgress();

                        if (exception instanceof UnknownHostException) {
                            iLoginView.showNoConnectionError();
                        } else {
                            iLoginView.showUnexpectedError();
                        }
                    });
                }
            });
        }
    }

    /**
     * Load saved email & password
     */
    void loadEmail() {
        runIfViewAttached((view) -> view.setEmail(mSharedPreferencesManager.get(Constants.UserCredentials.USER_EMAIL, "")));
    }

    /**
     * Check if user is logged in;
     *
     * @return Whether a user is logged in;
     */
    boolean isUserLoggedIn() {
        return mSharedPreferencesManager.get(Constants.UserCredentials.USER_LOGGED_IN, false);
    }

    /**
     * Store user's email on SharedPreferences;
     * @param email
     */
    void storeEmail(@NonNull String email) {
        mSharedPreferencesManager.store(Constants.UserCredentials.USER_EMAIL, email);
    }
}
