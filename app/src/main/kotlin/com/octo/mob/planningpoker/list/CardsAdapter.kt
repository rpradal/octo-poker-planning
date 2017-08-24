package com.octo.mob.planningpoker.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.octo.mob.planningpoker.list.model.Card

class CardsAdapter(val cardClickListener: CardClickListener) : RecyclerView.Adapter<CardViewHolder>() {

    private var cardList: List<Card>? = null

    override fun getItemCount(): Int = cardList?.size ?: 0

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        cardList?.let {
            holder?.bind(it[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        val view = ImageView(parent?.context)
        view.adjustViewBounds = true
        return CardViewHolder(view, cardClickListener)
    }

    fun setCards(resourceList: List<Card>) {
        cardList = resourceList
        notifyDataSetChanged()
    }

}