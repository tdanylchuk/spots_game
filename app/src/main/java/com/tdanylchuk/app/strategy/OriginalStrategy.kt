package com.tdanylchuk.app.strategy

import android.widget.Button
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.R

import com.tdanylchuk.app.model.Cell

class OriginalStrategy : ContentStrategy {

    override fun applyViewContent(cellButton: Button, cell: Cell, cellSize: Int) {
        cellButton.setBackgroundResource(R.drawable.cell)
        cellButton.text = (cell.y * FIELD_CELLS_SIZE + cell.x + 1).toString()
        cellButton.textSize = (cellSize / 5).toFloat()
    }
}
