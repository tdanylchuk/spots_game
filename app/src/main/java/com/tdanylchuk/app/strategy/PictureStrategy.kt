package com.tdanylchuk.app.strategy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.util.BitmapUtil

class PictureStrategy(private val imageResId: Int, x: Int, y: Int, context: Context) : AbstractContentStrategy(imageResId, x, y) {

    private val bitmapArray: Array<Array<Bitmap>>

    init {
        val picture = BitmapFactory.decodeResource(context.resources, imageResId)
        bitmapArray = BitmapUtil.cropImage(picture, x, y)
    }

    override fun applyViewContent(cellButton: Button, cell: Cell, cellSize: Int) {
        val context = cellButton.context
        cellButton.background = BitmapDrawable(context.resources, bitmapArray[cell.x][cell.y])
    }


}
