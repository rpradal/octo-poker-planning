package com.octo.mob.planningpoker.list

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import android.widget.ImageView
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.planningpoker.BuildConfig
import com.octo.mob.planningpoker.R
import com.octo.mob.planningpoker.detail.DetailActivity
import com.octo.mob.planningpoker.list.model.Card
import com.octo.mob.planningpoker.list.model.CardDeck
import com.octo.mob.planningpoker.transversal.AnalyticsSender
import com.octo.mob.planningpoker.transversal.AnalyticsSenderImpl
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.email


class MainActivity : AppCompatActivity() {

    companion object {
        val GRID_COLUMN_NUMBER = 3
    }

    private var currentCardType = CardDeck.FIBONACCI

    private val cardsAdapter = CardsAdapter(SelectedCardListener())
    private lateinit var analyticsSender: AnalyticsSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsRecyclerView.layoutManager = GridLayoutManager(this, GRID_COLUMN_NUMBER)
        cardsRecyclerView.adapter = cardsAdapter

        cardsAdapter.setCards(currentCardType.resourceList)

        analyticsSender = AnalyticsSenderImpl(FirebaseAnalytics.getInstance(this), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val sortIcon = ContextCompat.getDrawable(this, R.drawable.ic_view_agenda_white_24dp)
        val sortSubMenu = menu?.addSubMenu(Menu.NONE, Menu.NONE, Menu.NONE, R.string.selected_card_transition_name)?.setIcon(sortIcon)
        sortSubMenu?.item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        sortSubMenu?.add(Menu.NONE, R.id.menu_fibonacci, Menu.NONE, R.string.fibonacci_cards)
        sortSubMenu?.add(Menu.NONE, R.id.menu_tshirt, Menu.NONE, R.string.tshirt_cards)
        menu?.add(Menu.NONE, R.id.menu_feedback, Menu.NONE, R.string.send_feedback)?.setShowAsAction(SHOW_AS_ACTION_NEVER)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val fibonacciMenu = menu?.findItem(R.id.menu_fibonacci)
        if (currentCardType == CardDeck.FIBONACCI) {
            fibonacciMenu?.icon = ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp)
        } else {
            fibonacciMenu?.icon = null
        }

        val tshirtSizeMenu = menu?.findItem(R.id.menu_tshirt)
        if (currentCardType == CardDeck.TSHIRT) {
            tshirtSizeMenu?.icon = ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp)
        } else {
            tshirtSizeMenu?.icon = null
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_tshirt ->
                currentCardType = CardDeck.TSHIRT
            R.id.menu_fibonacci ->
                currentCardType = CardDeck.FIBONACCI
            R.id.menu_feedback -> {
                analyticsSender.onSendFeedBack()
                sendFeedbackEmail()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
        cardsAdapter.setCards(currentCardType.resourceList)
        analyticsSender.onCardDeckChanged(currentCardType)
        invalidateOptionsMenu()
        return true
    }

    private fun sendFeedbackEmail() {
        val email = getString(R.string.app_feedback_recipient_email)
        val subject = getString(R.string.app_feedback_subject_pattern, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        email(email, subject)
    }

    inner class SelectedCardListener : CardClickListener {
        override fun onClick(card: Card, clickedView: ImageView) {
            val transitionName = getString(R.string.selected_card_transition_name)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, clickedView, transitionName)
            analyticsSender.onCardSelected(card)
            startActivity(DetailActivity.getIntent(this@MainActivity, card.resource), options.toBundle())
        }
    }

}

