package com.octo.mob.planningpoker.list.model

import com.octo.mob.planningpoker.R

enum class CardType(val resourceList: List<Int>) {
    FIBONACCI(listOf(
            R.drawable.fibonacci_1,
            R.drawable.fibonacci_2,
            R.drawable.fibonacci_3,
            R.drawable.fibonacci_5,
            R.drawable.fibonacci_8,
            R.drawable.fibonacci_13,
            R.drawable.fibonacci_21,
            R.drawable.coffee
    ))
    ,
    TSHIRT(listOf(
            R.drawable.tshirt_xs,
            R.drawable.tshirt_s,
            R.drawable.tshirt_m,
            R.drawable.tshirt_l,
            R.drawable.tshirt_xl,
            R.drawable.coffee
    ))
}