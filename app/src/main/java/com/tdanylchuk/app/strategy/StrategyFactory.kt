package com.tdanylchuk.app.strategy


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object StrategyFactory {

    fun createPictureStrategy(context: Context, resourceId: Int, size: Int): ContentStrategy {
        val picture = BitmapFactory.decodeResource(context.resources, resourceId)
        return PictureStrategy(picture, size, size)
    }

    fun createOriginalStrategy(): ContentStrategy {
        return OriginalStrategy()
    }

}
