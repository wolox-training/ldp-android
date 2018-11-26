package ar.com.wolox.android.example.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;
import android.widget.TextView;

import ar.com.wolox.android.R;
import ar.com.wolox.android.example.ui.home.HomeActivity;
import ar.com.wolox.android.example.ui.signup.SignUpActivity;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import ar.com.wolox.wolmo.core.util.ToastFactory;
import butterknife.BindView;

/**
 * Login fragment;
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.fragment_login_login_button) Button mLoginButton;
    @BindView(R.id.fragment_login_sign_up_button) Button mSignUpButton;
    @BindView(R.id.fragment_login_email_input) EditText mEmailInput;
    @BindView(R.id.fragment_login_password_input) EditText mPasswordInput;
    @BindView(R.id.fragment_login_terms_and_conditions) TextView mTermsAndConditions;

    @Inject ToastFactory mToastFactory;

    ProgressDialog mProgressDialog;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {}

    @Override
    public void setUi(View v) {
        super.setUi(v);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.login_wait_a_moment));
    }

    @Override
    public void populate() {
        super.populate();

        if (getPresenter().isUserLoggedIn()) {
            onUserLoggedIn();

            try {
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getPresenter().loadEmail();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().storeEmail(mEmailInput.getText().toString());
    }

    @Override
    public void setUi(View v) {
        super.setUi(v);
        mTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void setListeners() {
        super.setListeners();
        mLoginButton.setOnClickListener(view -> onLoginClicked());
        mSignUpButton.setOnClickListener(view -> openSignUpActivity());
    }

    @Override
    public void showEmptyFormErrorOnEmail() {
        mEmailInput.requestFocus();
        mEmailInput.setError(getString(R.string.login_empty_form));
    }

    @Override
    public void showInvalidEmailError() {
        mEmailInput.requestFocus();
        mEmailInput.setError(getString(R.string.login_invalid_email));
    }

    @Override
    public void setEmail(@NonNull String email) {
        mEmailInput.setText(email);
    }

    @Override
    public void setPassword(@NonNull String password) {
        mPasswordInput.setText(password);
    }

    @Override
    public void onUserLoggedIn() {
        Intent homeIntent = new Intent(getContext(), HomeActivity.class);
        startActivity(homeIntent);

        try {
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWrongEmailOrPassword() {
        mToastFactory.show(R.string.login_invalid_credentials);
    }

    @Override
    public void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showNoConnectionError() {
        mToastFactory.showLong(R.string.login_user_no_connected);
    }

    @Override
    public void showUnexpectedError() {
        mToastFactory.show(R.string.app_unexpected_error);
    }

    @Override
    public void showEmptyFormErrorOnPassword() {
        mPasswordInput.requestFocus();
        mPasswordInput.setError(getString(R.string.login_empty_form));
    }

    void openSignUpActivity() {
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    void onLoginClicked() {
        String email = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showEmptyFormError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showInvalidEmailError();
        } else {
            getPresenter().login(email, password);
        }
    }
}
