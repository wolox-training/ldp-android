package ar.com.wolox.android.example.ui.signup;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;

/**
 * Sign up fragment;
 */
public class SignUpFragment extends WolmoFragment {

    @BindView(R.id.fragment_sign_up_join_button) Button mSigUpButton;
    @BindView(R.id.fragment_sign_up_terms_and_conditions) TextView mTermsAndConditions;

    @Override
    public int layout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void init() { }

    @Override
    public void setUi(View v) {
        super.setUi(v);
        mTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
