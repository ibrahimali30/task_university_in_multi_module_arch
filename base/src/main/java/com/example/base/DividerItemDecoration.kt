package com.example.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView


class DividerItemDecoration(context: Context, dividerHeight: Int = 2) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint()
    private val dividerHeight: Int = dividerHeight

    init {
        // Set divider color
        paint.color = context.resources.getColor(android.R.color.darker_gray)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}