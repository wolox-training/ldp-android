package ar.com.wolox.android.example.utils

import android.support.v7.widget.RecyclerView

abstract class InfiniteScroll(val performAction: () -> Unit) : RecyclerView.OnScrollListener() {

    var currentScroll = 0

    private val TAG: String = InfiniteScroll::class.java.simpleName

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE && currentScroll >= 0 && !isLoading() && hasMore()) performAction()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        currentScroll = dy
    }

    /**
     * If some data is being load currently;
     */
    abstract fun isLoading(): Boolean

    /**
     * When more items should be added to the [RecyclerView].
     *
     * It is a good place to define how the independent variables incurs on more items being added, for example, visible items on [RecyclerView.LayoutManager]
     */
    abstract fun hasMore(): Boolean
}