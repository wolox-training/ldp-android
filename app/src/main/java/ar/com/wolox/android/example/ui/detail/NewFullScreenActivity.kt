package ar.com.wolox.android.example.ui.detail

import android.os.Bundle
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import kotlinx.android.synthetic.main.activity_news_full_screen.*

class NewFullScreenActivity : WolmoActivity() {

    override fun init() {
        val pictureUrl = requireArguments().get(NEWS_PICTURE) as String
        mNewImage.setImageURI(pictureUrl)
    }

    override fun layout() = R.layout.activity_news_full_screen

    override fun handleArguments(args: Bundle?) = args?.containsKey(NEWS_PICTURE) ?: false

    override fun setUi() {
        super.setUi()
        window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    private fun requireArguments() = intent.extras!!

    companion object {
        const val NEWS_PICTURE = "news_picture"
    }
}