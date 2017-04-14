package com.octo.mob.planningpoker

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView

class CardsAdapter(val cardClickListener: CardClickListener) : RecyclerView.Adapter<CardViewHolder>() {

    val cardResources = listOf(R.drawable.fibonacci_1, R.drawable.fibonacci_2, R.drawable.fibonacci_3, R.drawable.fibonacci_5, R.drawable.fibonacci_8, R.drawable.fibonacci_13, R.drawable.fibonacci_21, R.drawable.coffee)

    override fun getItemCount(): Int = cardResources.size

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        holder?.bind(cardResources[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        return CardViewHolder(ImageView(parent!!.context), cardClickListener)
    }

}