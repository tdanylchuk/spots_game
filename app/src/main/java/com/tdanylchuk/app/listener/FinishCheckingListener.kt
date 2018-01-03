package com.tdanylchuk.app.listener

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.tdanylchuk.app.engine.Finisher

class FinishCheckingListener(private val finisher: Finisher) : AnimatorListenerAdapter() {

    @Volatile
    private var isFinished: Boolean = false

    override fun onAnimationEnd(animation: Animator) {
        if (!isFinished) {
            isFinished = true
            isFinished = finisher.tryFinish()
        }
    }

}
