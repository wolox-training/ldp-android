package ar.com.wolox.android.example.ui.signup;

import android.widget.Button;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import butterknife.BindView;

/**
 * Sign up fragment;
 */
public class SignUpFragment extends WolmoFragment {

    @BindView(R.id.fragment_sign_up_join_button) Button mSigUpButton;

    @Override
    public int layout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void init() {
    }
}
