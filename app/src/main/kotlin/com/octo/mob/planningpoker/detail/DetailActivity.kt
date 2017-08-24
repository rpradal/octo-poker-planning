package com.octo.mob.planningpoker.detail

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.planningpoker.R
import com.octo.mob.planningpoker.transversal.AnalyticsSender
import com.octo.mob.planningpoker.transversal.AnalyticsSenderImpl
import com.octo.mob.planningpoker.transversal.BaseAnimatorListener
import com.octo.mob.planningpoker.transversal.BaseTransitionListener
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), ShakeDetector.Listener {

    companion object {
        val SELECTED_CARD_BUNDLE_KEY = "SELECTED_CARD_BUNDLE_KEY"
        val ROTATION_ANIMATION_DURATION_MILLI = 300L
        val ROTATING_VIEW_CAMERA_DISTANCE = 10000F

        fun getIntent(context: Context, selectedDrawableRes: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SELECTED_CARD_BUNDLE_KEY, selectedDrawableRes)
            return intent
        }
    }

    private val shakeDetector = ShakeDetector(this)
    private var isRevealStarted = false
    private lateinit var analyticsSender: AnalyticsSender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val res = intent.getIntExtra(SELECTED_CARD_BUNDLE_KEY, 0)
        bigCardImageView.setImageDrawable(ContextCompat.getDrawable(this, res))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition.addListener(SharedElementTransitionListener())
        } else {
            getShowBackAnimator().start()
        }

        bigCardImageView.cameraDistance = ROTATING_VIEW_CAMERA_DISTANCE
        backCardImageView.cameraDistance = ROTATING_VIEW_CAMERA_DISTANCE

        analyticsSender = AnalyticsSenderImpl(FirebaseAnalytics.getInstance(this), this)

    }

    override fun onResume() {
        super.onResume()
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        shakeDetector.start(sensorManager)
    }

    override fun onPause() {
        shakeDetector.stop()
        super.onPause()
    }

    override fun onBackPressed() {
        if (bigCardImageView.rotationY != 0F) {
            val showFrontAnimator = getShowFrontAnimator()
            showFrontAnimator.addListener(object : BaseAnimatorListener() {
                override fun onAnimationEnd(p0: Animator?) {
                    finishAfterTransitionCompat()
                }
            })
            showFrontAnimator.start()
        } else {
            super.onBackPressed()
        }
    }

    override fun hearShake() {
        if (!isRevealStarted) {
            getShowFrontAnimator().start()
            isRevealStarted = true
        }
    }

    private fun getShowFrontAnimator(): Animator {

        val animatorSet = AnimatorSet()

        val hideBackRotationAnimator = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 0f, -90f)
        hideBackRotationAnimator.duration = ROTATION_ANIMATION_DURATION_MILLI

        val showFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 90f, 0f)
        showFrontRotationAnimator.duration = ROTATION_ANIMATION_DURATION_MILLI

        animatorSet.playSequentially(hideBackRotationAnimator, showFrontRotationAnimator)
        animatorSet.addListener(ClickDesactivatorAnimatorListener())
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        return animatorSet
    }

    private fun getShowBackAnimator(): Animator {

        val animatorSet = AnimatorSet()

        val hideFrontRotationAnimator = ObjectAnimator.ofFloat(bigCardImageView, "rotationY", 0f, -90f)
        hideFrontRotationAnimator.duration = ROTATION_ANIMATION_DURATION_MILLI

        val showBack = ObjectAnimator.ofFloat(backCardImageView, "alpha", 0f, 1f)
        showBack.duration = 0

        val showBackRotationAnimation = ObjectAnimator.ofFloat(backCardImageView, "rotationY", 90f, 0f)
        showBackRotationAnimation.duration = ROTATION_ANIMATION_DURATION_MILLI

        animatorSet.playSequentially(hideFrontRotationAnimator, showBack, showBackRotationAnimation)
        animatorSet.addListener(ClickDesactivatorAnimatorListener())
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        return animatorSet
    }

    private fun addClickListeners() {
        backCardImageView.setOnClickListener {
            analyticsSender.onEstimationRevealed()
            getShowFrontAnimator().start()
        }
        bigCardImageView.setOnClickListener {
            analyticsSender.onEstimationDismissed()
            finishAfterTransitionCompat()
        }
    }

    private fun finishAfterTransitionCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
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