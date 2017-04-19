package com.octo.mob.planningpoker.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView

class CardsAdapter(val cardClickListener: CardClickListener) : RecyclerView.Adapter<CardViewHolder>() {

    private var cardRessources: List<Int>? = null

    override fun getItemCount(): Int = cardRessources?.size ?: 0

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        cardRessources?.let {
            holder?.bind(it[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        return CardViewHolder(ImageView(parent?.context), cardClickListener)
    }

    fun setCards(resourceList: List<Int>) {
        cardRessources = resourceList
        notifyDataSetChanged()
    }

}