package com.tdanylchuk.app.strategy

import android.widget.Button

import com.tdanylchuk.app.model.Cell

interface ContentStrategy {

    fun applyViewContent(cellButton: Button, cell: Cell, cellSize: Int)

    fun getFinishImageId() : Int

    fun getFieldHeight() : Int

    fun getFieldWidth() : Int
}
