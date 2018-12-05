package ar.com.wolox.android.example.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

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

    private static final int GOOGLE_SIGN_IN = 101;

    @BindView(R.id.fragment_login_login_button) Button mLoginButton;
    @BindView(R.id.fragment_login_sign_up_button) Button mSignUpButton;
    @BindView(R.id.fragment_login_email_input) EditText mEmailInput;
    @BindView(R.id.fragment_login_password_input) EditText mPasswordInput;
    @BindView(R.id.fragment_login_terms_and_conditions) TextView mTermsAndConditions;
    @BindView(R.id.fragment_login_sign_in_google_button) SignInButton mGoogleSignInButton;

    @Inject ToastFactory mToastFactory;

    ProgressDialog mProgressDialog;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mGoogleSignInButton.getContext(), googleSignInOptions);
    }

    @Override
    public void setUi(View v) {
        super.setUi(v);

        mTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.login_wait_a_moment));

        mGoogleSignInButton.setSize(SignInButton.SIZE_WIDE);
    }

    @Override
    public void populate() {
        super.populate();

        if (getPresenter().isUserLoggedIn() || GoogleSignIn.getLastSignedInAccount(mGoogleSignInButton.getContext()) != null) {
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
    public void setListeners() {
        super.setListeners();
        mLoginButton.setOnClickListener(view -> onLoginClicked());
        mSignUpButton.setOnClickListener(view -> openSignUpActivity());
        mGoogleSignInButton.setOnClickListener(view -> onGoogleSignInClicked());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        hideProgress();

        if (requestCode == GOOGLE_SIGN_IN && resultCode != Activity.RESULT_CANCELED) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInWithGoogleResult(task);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void openSignUpActivity() {
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    void onLoginClicked() {
        String email = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            showEmptyFormErrorOnEmail();
        } else if (email.isEmpty()) {
            showEmptyFormErrorOnEmail();
        } else if (password.isEmpty()) {
            showEmptyFormErrorOnPassword();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showInvalidEmailError();
        } else {
            getPresenter().login(email, password);
        }
    }

    public void onGoogleSignInClicked() {
        if (mGoogleSignInClient == null) {
            mToastFactory.showLong(R.string.app_unexpected_error);
        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            showProgress();
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        }
    }

    public void handleSignInWithGoogleResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mToastFactory.showLong(getString(R.string.login_user_signing_with_google_account) + " " + account.getEmail());
            onUserLoggedIn();
        } catch (ApiException e) {
            mToastFactory.showLong(String.format("%s %s", getString(R.string.app_unexpected_error), e.getMessage()));
        }
    }
}
