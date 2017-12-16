package com.tdanylchuk.app.listener

import android.view.GestureDetector
import android.view.MotionEvent
import com.tdanylchuk.app.listener.SwipeListener

class GestureListener(private val swipeThreshold: Int,
                      private val swipeVelocity: Int,
                      private val swipeListener: SwipeListener) : GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent): Boolean = true

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        var result = false
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocity) {
                    if (diffX > 0) {
                        swipeListener.onSwipeRight()
                    } else {
                        swipeListener.onSwipeLeft()
                    }
                    result = true
                }
            } else if (Math.abs(diffY) > swipeThreshold && Math.abs(velocityY) > swipeThreshold) {
                if (diffY > 0) {
                    swipeListener.onSwipeBottom()
                } else {
                    swipeListener.onSwipeTop()
                }
                result = true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }
}
