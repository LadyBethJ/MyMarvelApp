package com.mjb.core.extensions

import androidx.recyclerview.widget.RecyclerView
import com.mjb.core.utils.InfiniteScroll

fun RecyclerView.infiniteScroll(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(InfiniteScroll(this, visibleThreshold, loadMore))
}