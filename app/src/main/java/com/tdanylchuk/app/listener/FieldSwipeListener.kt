package com.tdanylchuk.app.listener

import com.tdanylchuk.app.GameplayConstants
import com.tdanylchuk.app.engine.Game


class FieldSwipeListener(private val game: Game) : SwipeListener {

    override fun onSwipeRight() {
        val emptyCell = game.field.emptyCell()
        if (emptyCell.x - 1 >= 0) {
            game.moveCell(game.field.cell(emptyCell.x - 1, emptyCell.y), emptyCell)
        }
    }

    override fun onSwipeLeft() {
        val emptyCell = game.field.emptyCell()
        if (emptyCell.x + 1 <= GameplayConstants.FIELD_CELLS_SIZE - 1) {
            game.moveCell(game.field.cell(emptyCell.x + 1, emptyCell.y), emptyCell)
        }
    }

    override fun onSwipeTop() {
        val emptyCell = game.field.emptyCell()
        if (emptyCell.y + 1 <= GameplayConstants.FIELD_CELLS_SIZE - 1) {
            game.moveCell(game.field.cell(emptyCell.x, emptyCell.y + 1), emptyCell)
        }
    }

    override fun onSwipeBottom() {
        val emptyCell = game.field.emptyCell()
        if (emptyCell.y - 1 >= 0) {
            game.moveCell(game.field.cell(emptyCell.x, emptyCell.y - 1), emptyCell)
        }
    }

}