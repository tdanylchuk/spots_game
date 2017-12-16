package com.tdanylchuk.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.tdanylchuk.app.model.Field

class FinishCheckingListener(private val field: Field,
                             private val context: Context,
                             private val imageResId: Int) : AnimatorListenerAdapter() {

    override fun onAnimationEnd(animation: Animator) {
        if (field.correct()) {
            val intent = Intent(context, FinishActivity::class.java)
            intent.putExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, imageResId)
            startActivity(context, intent, null)
        }
    }

}
