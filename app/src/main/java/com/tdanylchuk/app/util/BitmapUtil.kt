package com.tdanylchuk.app.util


import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap

object BitmapUtil {

    fun cropImage(source: Bitmap, x: Int, y: Int): Array<Array<Bitmap>> {
        val partHeight = source.height / x
        val partWidth = source.width / y
        return Array(x, { i ->
            Array<Bitmap>(y, { j ->
                createBitmap(source, i * partWidth, j * partHeight, partWidth, partHeight)
            })
        })
    }
}

