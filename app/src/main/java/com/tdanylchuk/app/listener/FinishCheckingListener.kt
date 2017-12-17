package com.tdanylchuk.app.listener

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.tdanylchuk.app.FinishActivity
import com.tdanylchuk.app.IntentConstants
import com.tdanylchuk.app.model.Field

class FinishCheckingListener(private val field: Field,
                             private val context: Context,
                             private val imageResId: Int) : AnimatorListenerAdapter() {

    @Volatile
    private var isFinished: Boolean = false

    override fun onAnimationEnd(animation: Animator) {
        if (field.correct() && !isFinished) {
            isFinished = true
            val intent = Intent(context, FinishActivity::class.java)
            intent.putExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, imageResId)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            Log.d(javaClass.simpleName, "Staring finish Activity.")
            startActivity(context, intent, null)
        }
    }

}
