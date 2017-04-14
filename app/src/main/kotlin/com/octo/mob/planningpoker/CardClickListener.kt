package com.octo.mob.planningpoker

import android.widget.ImageView

interface CardClickListener {
    fun onClick(drawableRes: Int, view: ImageView)
}