package ar.com.wolox.android.example.ui.login;

import android.support.annotation.NonNull;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.annotation.Nonnull;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;

/**
 * Login fragment;
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.fragment_login_login_button) Button mLoginButton;
    @BindView(R.id.fragment_login_sign_up_button) Button mSignUpButton;
    @BindView(R.id.fragment_login_email_input) EditText mEmailInput;
    @BindView(R.id.fragment_login_password_input) EditText mPasswordInput;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {}

    @Override
    public void populate() {
        super.populate();
        getPresenter().loadEmailAndPassword();
    }

    @Override
    public void setListeners() {
        super.setListeners();
        mLoginButton.setOnClickListener(view -> onLoginClicked());
    }

    @Override
    public void showEmptyFormError() {
        Toast.makeText(getContext(), R.string.login_empty_form, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInvalidEmailError() {
        mEmailInput.requestFocus();
        mEmailInput.setError(getString(R.string.login_invalid_email));
    }

    @Override
    public void setEmail(@Nonnull String email) {
        mEmailInput.setText(email);
    }

    @Override
    public void setPassword(@NonNull String password) {
        mPasswordInput.setText(password);
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
