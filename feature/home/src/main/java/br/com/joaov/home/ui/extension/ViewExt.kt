package br.com.joaov.home.ui.extension

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.joaov.home.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout


internal fun ShimmerFrameLayout.showAnimation(show: Boolean) {
    if (show) {
        startShimmerAnimation()
        
    } else {
        stopShimmerAnimation()
    }
    isVisible = show
}

internal fun ImageView.loadByUrl(url: String?) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_dots)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .error(R.drawable.ic_alert)
        .into(this)
}

internal fun RecyclerView.setOnLastItemListener(listener: () -> Unit) {
    val onLast = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                listener()
            }
        }
    }
    addOnScrollListener(onLast)
}



