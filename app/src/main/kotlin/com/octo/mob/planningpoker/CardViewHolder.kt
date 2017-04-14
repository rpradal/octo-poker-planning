package com.octo.mob.planningpoker

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

class CardViewHolder(val view: ImageView, val onClickListener: CardClickListener) : RecyclerView.ViewHolder(view) {
    fun bind(drawableRes: Int) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context, drawableRes))
        view.setOnClickListener { onClickListener.onClick(drawableRes, view) }
    }
}