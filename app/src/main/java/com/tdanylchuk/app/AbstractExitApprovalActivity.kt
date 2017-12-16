package com.tdanylchuk.app

import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


abstract class AbstractExitApprovalActivity : AppCompatActivity() {

    private var exit: Boolean? = false

    override fun onBackPressed() {
        if (exit!!) {
            finishAction()
        } else {
            Toast.makeText(this, getApprovalQuestion(), Toast.LENGTH_SHORT).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3 * 1000)
        }
    }

    open fun finishAction() {
        finish()
    }

    open fun getApprovalQuestion(): String {
        return "Press Back again to Exit."
    }
}