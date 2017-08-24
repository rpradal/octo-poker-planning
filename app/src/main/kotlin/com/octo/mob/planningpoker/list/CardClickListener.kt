package com.octo.mob.planningpoker.list

import android.widget.ImageView
import com.octo.mob.planningpoker.list.model.Card

interface CardClickListener {
    fun onClick(card: Card, clickedView: ImageView)
}