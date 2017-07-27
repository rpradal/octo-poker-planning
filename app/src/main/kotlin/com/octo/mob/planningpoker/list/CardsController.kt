package com.octo.mob.planningpoker.list

import com.octo.mob.planningpoker.list.model.CardType
import com.octo.mob.planningpoker.settings.SettingsRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class CardsController(val cardsPresenter: CardsPresenter, val settingsRepository: SettingsRepository) {

    var currentCardType: CardType? = null
        private set(value) {
            value?.let {
                field = value
                settingsRepository.saveCardType(value)
            }
        }

    fun showDefaultCardType(){
        launch(UI){
            val cardType = fetchCardType().await()
            showCardType(cardType)
        }
    }

    fun showCardType(cardType: CardType) {
        currentCardType = cardType
        cardsPresenter.presentCardType(cardType)
    }

    private fun fetchCardType() : Deferred<CardType> {
        return async(CommonPool){
            settingsRepository.getCardType()
        }
    }
}