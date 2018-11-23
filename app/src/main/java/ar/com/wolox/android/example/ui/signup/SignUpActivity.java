package ar.com.wolox.android.example.ui.signup;

import android.support.v7.widget.Toolbar;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.activity.WolmoActivity;
import butterknife.BindView;

/**
 * Sig Up activity;
 */
public class SignUpActivity extends WolmoActivity {

     @BindView(R.id.activity_sign_up_toolbar) Toolbar mToolbar;

    @Override
    protected int layout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void init() {
        replaceFragment(R.id.activity_sign_up_fragment_container, new SignUpFragment());
    }
}
