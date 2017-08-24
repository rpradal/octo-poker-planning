package com.octo.mob.planningpoker.list.model

import android.support.annotation.DrawableRes
import com.octo.mob.planningpoker.R

enum class CardDeck(val resourceList: List<Card>) {
    FIBONACCI(listOf(
            Card.ONE,
            Card.TWO,
            Card.THREE,
            Card.FIVE,
            Card.HEIGHT,
            Card.THIRTEEN,
            Card.TWENTY_ONE,
            Card.COFFEE
    ))
    ,
    TSHIRT(listOf(
            Card.XS,
            Card.S,
            Card.M,
            Card.L,
            Card.XL,
            Card.COFFEE
    ))
}

enum class Card(@DrawableRes val resource: Int) {
    ONE(R.drawable.fibonacci_1),
    TWO(R.drawable.fibonacci_2),
    THREE(R.drawable.fibonacci_3),
    FIVE(R.drawable.fibonacci_5),
    HEIGHT(R.drawable.fibonacci_8),
    THIRTEEN(R.drawable.fibonacci_13),
    TWENTY_ONE(R.drawable.fibonacci_21),

    XS(R.drawable.tshirt_xs),
    S(R.drawable.tshirt_s),
    M(R.drawable.tshirt_m),
    L(R.drawable.tshirt_l),
    XL(R.drawable.tshirt_xl),

    COFFEE(R.drawable.coffee)

}