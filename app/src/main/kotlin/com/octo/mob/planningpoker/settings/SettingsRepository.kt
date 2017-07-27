package com.octo.mob.planningpoker.settings

import android.content.SharedPreferences
import com.octo.mob.planningpoker.list.model.CardType

interface SettingsRepository {
    fun getCardType(): CardType
    fun saveCardType(cardType: CardType)
}

class SettingsRepositoryImpl(val sharedPreferences: SharedPreferences) : SettingsRepository {

    companion object {
        internal val DEFAULT_CARD_TYPE = CardType.FIBONACCI
        internal val CARD_TYPE_KEY = "CARD_TYPE_KEY"
    }

    override fun getCardType(): CardType {
        try {
            val cardType = sharedPreferences.getString(CARD_TYPE_KEY, DEFAULT_CARD_TYPE.name)
            return CardType.valueOf(cardType)
        } catch (exception: IllegalArgumentException) {
            return DEFAULT_CARD_TYPE
        }
    }

    override fun saveCardType(cardType: CardType) {
        sharedPreferences.edit()
                .putString(CARD_TYPE_KEY, cardType.name)
                .apply()
    }

}