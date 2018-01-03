package com.tdanylchuk.app.engine

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.tdanylchuk.app.GameplayConstants
import com.tdanylchuk.app.listener.FinishCheckingListener
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.model.Field

class FieldManipulator(private val field: Field, finisher: Finisher) {

    private val finishCheckListener = FinishCheckingListener(finisher)

    fun tryMoveCell(cell: Cell) {
        if (cell.isNotEmpty()) {
            if (tryMoveTop(cell)) return
            if (tryMoveBottom(cell)) return
            if (tryMoveLeft(cell)) return
            if (tryMoveRight(cell)) return
        }
    }

    fun shuffle() {
//        var emptyCell = field.emptyCell()
//        for (i in 0..50) run {
//            emptyCell
//        }
    }

    private fun tryMoveTop(cellToMove: Cell): Boolean =
            cellToMove.x > 0 && tryMove(cellToMove, cellToMove.x - 1, cellToMove.y)

    private fun tryMoveBottom(cellToMove: Cell): Boolean =
            cellToMove.x < GameplayConstants.FIELD_CELLS_SIZE - 1 && tryMove(cellToMove, cellToMove.x + 1, cellToMove.y)

    private fun tryMoveLeft(cellToMove: Cell): Boolean =
            cellToMove.y > 0 && tryMove(cellToMove, cellToMove.x, cellToMove.y - 1)

    private fun tryMoveRight(cellToMove: Cell): Boolean =
            cellToMove.y < GameplayConstants.FIELD_CELLS_SIZE - 1 && tryMove(cellToMove, cellToMove.x, cellToMove.y + 1)

    private fun tryMove(cellToMove: Cell, x: Int, y: Int): Boolean {
        val checking = field.cell(x, y)
        if (checking.isEmpty()) {
            move(cellToMove, checking)
            return true
        }
        return false
    }

    fun moveRight() {
        val emptyCell = field.emptyCell()
        if (emptyCell.x - 1 >= 0) {
            move(field.cell(emptyCell.x - 1, emptyCell.y), emptyCell)
        }
    }

    fun moveLeft() {
        val emptyCell = field.emptyCell()
        if (emptyCell.x + 1 <= GameplayConstants.FIELD_CELLS_SIZE - 1) {
            move(field.cell(emptyCell.x + 1, emptyCell.y), emptyCell)
        }
    }

    fun moveTop() {
        val emptyCell = field.emptyCell()
        if (emptyCell.y + 1 <= GameplayConstants.FIELD_CELLS_SIZE - 1) {
            move(field.cell(emptyCell.x, emptyCell.y + 1), emptyCell)
        }
    }

    fun moveBottom() {
        val emptyCell = field.emptyCell()
        if (emptyCell.y - 1 >= 0) {
            move(field.cell(emptyCell.x, emptyCell.y - 1), emptyCell)
        }
    }

    private fun move(cellToMove: Cell, destinationCell: Cell) {
        move(cellToMove, destinationCell, GameplayConstants.ANIMATION_SPEED)
    }

    private fun move(cellToMove: Cell, destinationCell: Cell, duration: Long) {
        val currentPart = cellToMove.part
        val currentView = currentPart!!.view
        val cellSize = currentView.width

        val animX = ObjectAnimator.ofFloat(currentView, "x", (destinationCell.x * cellSize).toFloat())
        val animY = ObjectAnimator.ofFloat(currentView, "y", (destinationCell.y * cellSize).toFloat())

        val animator = createAnimator(duration)
        animator.playTogether(animX, animY)
        animator.start()

        cellToMove.part = destinationCell.part
        destinationCell.part = currentPart

        currentView.tag = destinationCell
    }

    private fun createAnimator(duration: Long): AnimatorSet {
        val animator = AnimatorSet()
        animator.duration = duration
        animator.addListener(finishCheckListener)
        return animator
    }

}