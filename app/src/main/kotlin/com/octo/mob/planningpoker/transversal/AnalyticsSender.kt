package com.octo.mob.planningpoker.transversal

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.planningpoker.R
import com.octo.mob.planningpoker.list.model.Card
import com.octo.mob.planningpoker.list.model.CardDeck

interface AnalyticsSender {
    fun onSendFeedBack()
    fun onEstimationDismissed()
    fun onEstimationRevealed()
    fun onCardSelected(card: Card)
    fun onCardDeckChanged(currentCardDeck: CardDeck)
}

class AnalyticsSenderImpl(private val firebaseAnalytics: FirebaseAnalytics,
                          private val context: Context) : AnalyticsSender {
    override fun onCardDeckChanged(currentCardDeck: CardDeck) {
        val deckAnalyticsTag = getDeckAnalyticsTag(currentCardDeck)
        val eventParams = Pair(context.getString(R.string.deck_type_key), context.getString(deckAnalyticsTag))
        logFirebaseEvent(context.getString(R.string.on_deck_changed), eventParams)
    }

    override fun onCardSelected(card: Card) {
        val cardAnalyticsTag = getCardAnalyticsTag(card)
        val eventParams = Pair(context.getString(R.string.estimation_key), context.getString(cardAnalyticsTag))
        logFirebaseEvent(context.getString(R.string.on_card_choice), eventParams)
    }

    override fun onEstimationRevealed() {
        logFirebaseEvent(context.getString(R.string.on_estimation_revealed))
    }

    override fun onEstimationDismissed() {
        logFirebaseEvent(context.getString(R.string.on_estimation_dismissed))
    }

    override fun onSendFeedBack() {
        logFirebaseEvent(context.getString(R.string.on_feedback))
    }

    private fun logFirebaseEvent(event: String, param: Pair<String, String>? = null) {
        val eventBundle: Bundle = Bundle()

        param?.let {
            eventBundle.putString(it.first, it.second)
        }

        firebaseAnalytics.logEvent(event, eventBundle)
    }

    @StringRes
    private fun getDeckAnalyticsTag(deck: CardDeck): Int {
        return when (deck) {
            CardDeck.FIBONACCI -> R.string.deck_fibonacci
            CardDeck.TSHIRT -> R.string.deck_tshirt
        }
    }

    @StringRes
    private fun getCardAnalyticsTag(card: Card): Int {
        return when (card) {
            Card.ONE -> R.string.card_one
            Card.TWO -> R.string.card_two
            Card.THREE -> R.string.card_three
            Card.FIVE -> R.string.card_five
            Card.HEIGHT -> R.string.card_height
            Card.THIRTEEN -> R.string.card_thirteen
            Card.TWENTY_ONE -> R.string.card_twenty_one

            Card.XS -> R.string.card_xs
            Card.S -> R.string.card_s
            Card.M -> R.string.card_m
            Card.L -> R.string.card_l
            Card.XL -> R.string.card_xl

            Card.COFFEE -> R.string.card_coffee
        }
    }
}