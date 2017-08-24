package com.octo.mob.planningpoker.list

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.octo.mob.planningpoker.list.model.Card

class CardViewHolder(val view: ImageView, val onClickListener: CardClickListener) : RecyclerView.ViewHolder(view) {
    fun bind(card: Card) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context, card.resource))
        view.setOnClickListener { onClickListener.onClick(card, view) }
    }
}