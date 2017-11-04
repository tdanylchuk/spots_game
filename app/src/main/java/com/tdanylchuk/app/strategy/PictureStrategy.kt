package com.tdanylchuk.app.strategy

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.util.BitmapUtil

class PictureStrategy(sourceImage: Bitmap, x: Int, y: Int) : ContentStrategy {

    private val bitmapArray: Array<Array<Bitmap>> = BitmapUtil.cropImage(sourceImage, x, y)

    override fun applyViewContent(cellButton: Button, cell: Cell, cellSize: Int) {
        val context = cellButton.context
        cellButton.background = BitmapDrawable(context.resources, bitmapArray[cell.x][cell.y])
    }
}
