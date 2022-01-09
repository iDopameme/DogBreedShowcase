package com.example.dogbreedshowcase.api

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreedshowcase.R

fun RecyclerView.recyclerViewLineDivider(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    val itemDivider = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(context.getDrawable(R.drawable.line_recycler_view_divider)!!)
    }
    layoutManager = linearLayoutManager
    addItemDecoration(itemDivider)
}
