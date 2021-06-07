package com.mjb.mymarvelapp.presentation.utils.extensions

import androidx.recyclerview.widget.RecyclerView
import com.mjb.mymarvelapp.presentation.utils.helpers.InfiniteScroll

fun RecyclerView.infiniteScroll(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(InfiniteScroll(this, visibleThreshold, loadMore))
}