package com.octo.mob.planningpoker

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val SELECTED_CARD_BUNDLE_KEY = "SELECTED_CARD_BUNDLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val res = intent.getIntExtra(SELECTED_CARD_BUNDLE_KEY, 0)
        bigCardImageView.setImageDrawable(ContextCompat.getDrawable(this, res))

    }
}