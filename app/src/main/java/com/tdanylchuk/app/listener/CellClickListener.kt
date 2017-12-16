package com.tdanylchuk.app.listener

import android.view.View
import com.tdanylchuk.app.GameplayConstants
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.engine.Game

class CellClickListener(private val game: Game) : View.OnClickListener {

    override fun onClick(v: View?) {
        onClick((v!!.parent as View).tag as Cell)
    }

    private fun onClick(cell: Cell) {
        try {
            if (cell.isNotEmpty()) {
                val x = cell.x
                val y = cell.y
                if (x > 0) {
                    tryMove(cell, x - 1, y)
                }
                if (x < GameplayConstants.FIELD_CELLS_SIZE - 1) {
                    tryMove(cell, x + 1, y)
                }
                if (y > 0) {
                    tryMove(cell, x, y - 1)
                }
                if (y < GameplayConstants.FIELD_CELLS_SIZE - 1) {
                    tryMove(cell, x, y + 1)
                }
            }
        } catch (e: Exception) {
        }
    }

    @Throws(Exception::class)
    private fun tryMove(cell: Cell, x: Int, y: Int) {
        val checking = game.field.cell(x, y)
        if (checking.isEmpty()) {
            game.moveCell(cell, checking)
            throw Exception()
        }
    }

}
