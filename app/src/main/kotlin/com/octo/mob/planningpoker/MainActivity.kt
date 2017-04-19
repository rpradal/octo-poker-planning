package com.octo.mob.planningpoker

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        val GRID_COLUMN_NUMBER = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsRecyclerView.layoutManager = GridLayoutManager(this, GRID_COLUMN_NUMBER)
        cardsRecyclerView.adapter = CardsAdapter(SelectedCardListener())
    }

    inner class SelectedCardListener : CardClickListener {
        override fun onClick(drawableRes: Int, clickedView: ImageView) {
            val transitionName = getString(R.string.selected_card_transition_name)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, clickedView, transitionName)
            startActivity(DetailActivity.getIntent(this@MainActivity, drawableRes), options.toBundle())
        }
    }
}

