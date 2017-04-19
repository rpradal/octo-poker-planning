package com.octo.mob.planningpoker.detail

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import com.octo.mob.planningpoker.transversal.BaseAnimatorListener
import com.octo.mob.planningpoker.transversal.BaseTransitionListener
import com.octo.mob.planningpoker.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val SELECTED_CARD_BUNDLE_KEY = "SELECTED_CARD_BUNDLE_KEY"
        val ROTATION_ANITION_DURATION_MILLI = 300L

        fun getIntent(context: Context, selectedDrawableRes: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SELECTED_CARD_BUNDLE_KEY, selectedDrawableRes)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val res = intent.getIntExtra(SELECTED_CARD_BUNDLE_KEY, 0)
        bigCardImageView.setImageDrawable(ContextCompat.getDrawable(this, res))
        window.sharedElementEnterTransition.addListener(SharedElementTransitionListener())

    }

    override fun onBackPressed() {
        val showFrontAnimator = getShowFrontAnimator()
        showFrontAnimator.addListener(object : BaseAnimatorListener() {
            override fun onAnimationEnd(p0: Animator?) {
                finishAfterTransition()
            }
        })
        showFrontAnimator.start()
    }

    private fun getShowFrontAnimator(): Animator {

        val animatorSet = AnimatorSet()

        val hideBackRotationAnimator = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 0f, -90f)
        hideBackRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        val showFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 90f, 0f)
        showFrontRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        animatorSet.playSequentially(hideBackRotationAnimator, showFrontRotationAnimator)
        animatorSet.addListener(ClickDesactivatorAnimatorListener())
        return animatorSet
    }

    private fun getShowBackAnimator(): Animator {

        val animatorSet = AnimatorSet()

        val hideFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 0f, -90f)
        hideFrontRotationAnimator.duration = ROTATION_ANITION_DURATION_MILLI

        val showBack = ObjectAnimator.ofFloat(backCardImageView, "alpha", 0f, 1f)
        showBack.duration = 0

        val showBackRotationAnimation = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 90f, 0f)
        showBackRotationAnimation.duration = ROTATION_ANITION_DURATION_MILLI

        animatorSet.playSequentially(hideFrontRotationAnimator, showBack, showBackRotationAnimation)
        animatorSet.addListener(ClickDesactivatorAnimatorListener())
        return animatorSet
    }

    private fun addClickListeners() {
        backCardImageView.setOnClickListener { getShowFrontAnimator().start() }
        bigCardImageView.setOnClickListener { finishAfterTransition() }
    }

    private fun removeClickListeners() {
        backCardImageView.setOnClickListener(null)
        bigCardImageView.setOnClickListener(null)
    }

    inner class SharedElementTransitionListener : BaseTransitionListener() {
        override fun onTransitionEnd(p0: Transition?) {
            getShowBackAnimator().start()
        }
    }

    inner class ClickDesactivatorAnimatorListener : BaseAnimatorListener() {
        override fun onAnimationEnd(p0: Animator?) {
            addClickListeners()
        }

        override fun onAnimationStart(p0: Animator?) {
            removeClickListeners()
        }
    }

}