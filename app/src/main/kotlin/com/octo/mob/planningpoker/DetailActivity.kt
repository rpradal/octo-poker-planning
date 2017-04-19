package com.octo.mob.planningpoker

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val SELECTED_CARD_BUNDLE_KEY = "SELECTED_CARD_BUNDLE_KEY"
        val ROTATION_ANITION_DURATION_MILLI = 400L

        fun getIntent(context: Context, selectedDrawableRes: Int) : Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.SELECTED_CARD_BUNDLE_KEY, selectedDrawableRes)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val res = intent.getIntExtra(SELECTED_CARD_BUNDLE_KEY, 0)
        bigCardImageView.setImageDrawable(ContextCompat.getDrawable(this, res))
        window.sharedElementEnterTransition.addListener(SharedElementTransitionListener())

        backCardImageView.setOnClickListener { showFrontAnimation() }
        bigCardImageView.setOnClickListener { finishAfterTransition() }

    }

    private fun showFrontAnimation() {

        val animatorSet = AnimatorSet()

        val hideBackRotationAnimator = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 0f, -90f)
        hideBackRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        val showFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 90f, 0f)
        showFrontRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        animatorSet.playSequentially(hideBackRotationAnimator, showFrontRotationAnimator)
        animatorSet.start()
    }

    fun showBackAnimation() {

        val animatorSet = AnimatorSet()

        val hideFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 0f, -90f)
        hideFrontRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        val showBack = ObjectAnimator.ofFloat(backCardImageView, "alpha", 0f, 1f)
        showBack.duration = 0

        val showBackRotationAnimation = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 90f, 0f)
        showBackRotationAnimation.duration = ROTATION_ANITION_DURATION_MILLI

        animatorSet.playSequentially(hideFrontRotationAnimator, showBack, showBackRotationAnimation)
        animatorSet.start()

    }

    inner class SharedElementTransitionListener : Transition.TransitionListener {
        override fun onTransitionEnd(p0: Transition?) {
            showBackAnimation()
        }

        override fun onTransitionResume(p0: Transition?) {
        }

        override fun onTransitionPause(p0: Transition?) {
        }

        override fun onTransitionCancel(p0: Transition?) {
        }

        override fun onTransitionStart(p0: Transition?) {
        }
    }

}