package ar.com.wolox.android.example.customViews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import ar.com.wolox.android.R;

/**
 *
 */
public class TermsAndConditions extends LinearLayout {

    private Context mContext;

    public TermsAndConditions(Context context) {
        super(context);
        this.mContext = context;

        init();
    }

    public TermsAndConditions(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        init();
    }

    public TermsAndConditions(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    /**
     *
     */
    public void init() {
        inflate(getContext(), R.layout.sign_up_terms_view, this);
        findViewById(R.id.sign_up_conditions_text)
                .setOnClickListener(view -> {
                    String url = mContext.getString(R.string.sign_up_terms_url);
                    Uri uri = Uri.parse(url);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);

                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, R.string.app_not_found, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
