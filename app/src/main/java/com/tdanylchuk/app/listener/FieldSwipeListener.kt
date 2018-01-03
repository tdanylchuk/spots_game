package com.tdanylchuk.app.listener

import com.tdanylchuk.app.engine.FieldManipulator


class FieldSwipeListener(private val fieldManipulator: FieldManipulator) : SwipeListener {

    override fun onSwipeRight() {
        fieldManipulator.moveRight()
    }

    override fun onSwipeLeft() {
        fieldManipulator.moveLeft()
    }

    override fun onSwipeTop() {
        fieldManipulator.moveTop()
    }

    override fun onSwipeBottom() {
        fieldManipulator.moveBottom()
    }

}