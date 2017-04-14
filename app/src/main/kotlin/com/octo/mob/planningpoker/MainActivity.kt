package com.octo.mob.planningpoker

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CardClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsRecyclerView.layoutManager = GridLayoutManager(this, 3)
        cardsRecyclerView.adapter = CardsAdapter(this)

        backCardImageView.setOnClickListener {
            selectedCardImageView.visibility = View.VISIBLE
            backCardImageView.visibility = View.GONE
        }

        selectedCardImageView.setOnClickListener { selectedCardImageView.visibility = View.GONE }
    }

    override fun onClick(drawableRes: Int, view: ImageView) {
        //selectedCardImageView.setImageDrawable(ContextCompat.getDrawable(this, drawableRes))
        //backCardImageView.visibility = View.VISIBLE
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("FOO", drawableRes)
        startActivity(intent, options.toBundle())
    }
}

