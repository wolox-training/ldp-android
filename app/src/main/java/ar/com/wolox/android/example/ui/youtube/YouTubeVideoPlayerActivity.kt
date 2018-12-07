package ar.com.wolox.android.example.ui.youtube

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.SupportedYouTubeObject
import ar.com.wolox.android.example.utils.Extras
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import ar.com.wolox.wolmo.core.util.ToastFactory
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_youtube_video_player.*
import javax.inject.Inject

class YouTubeVideoPlayerActivity @Inject constructor() : WolmoActivity(), YouTubePlayer.OnInitializedListener {

    @Inject lateinit var mToastFactory: ToastFactory

    private val mPlayerFragment = YouTubePlayerSupportFragment()

    override fun handleArguments(args: Bundle?) = args?.containsKey(ITEM_OBJECT) ?: false

    override fun init() {
        replaceFragment(mFragmentContainer.id, mPlayerFragment)
        mPlayerFragment.initialize(getString(R.string.app_youtube_api_key), this)
    }

    override fun layout() = R.layout.activity_youtube_video_player

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        youTubePlayer?.run {
            val resultObject = requiredArguments().getSerializable(ITEM_OBJECT) as SupportedYouTubeObject
            when (resultObject.kind) {
                Extras.YouTubeResultKind.KIND_PLAYLIST -> youTubePlayer.loadPlaylist(resultObject.playlistId)
                Extras.YouTubeResultKind.KIND_VIDEO -> youTubePlayer.loadVideo(resultObject.videoId)
                else -> onNoPlayableResult()
            }
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) = mToastFactory.showLong(R.string.app_unexpected_error)

    private fun onNoPlayableResult() {
        mToastFactory.showLong(R.string.youtube_not_supported_result)
        finish()
    }

    private fun requiredArguments() = intent.extras!!

    companion object {
        const val ITEM_OBJECT = "item_object"
    }
}