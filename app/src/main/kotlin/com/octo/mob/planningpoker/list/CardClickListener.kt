package com.octo.mob.planningpoker.list

import android.widget.ImageView

interface CardClickListener {
    fun onClick(drawableRes: Int, clickedView: ImageView)
}