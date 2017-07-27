package com.octo.mob.planningpoker.list

import com.octo.mob.planningpoker.list.model.CardType

interface CardsPresenter {
    fun presentCardType(cardType: CardType)
}

class CardsPresenterImpl(val cardsScreen: CardsScreen) : CardsPresenter {
    override fun presentCardType(cardType: CardType) {
        cardsScreen.displayCardType(cardType)
    }
}