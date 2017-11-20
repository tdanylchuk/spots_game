package com.tdanylchuk.app.strategy

abstract class AbstractContentStrategy(private val imageResId: Int,
                                       private val x: Int,
                                       private val y: Int) : ContentStrategy {

    override fun getFieldHeight(): Int = x
    override fun getFieldWidth(): Int = y
    override fun getFinishImageId(): Int = imageResId

}
