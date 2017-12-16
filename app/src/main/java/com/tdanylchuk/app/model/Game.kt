package com.tdanylchuk.app.model

import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.tdanylchuk.app.*
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.sound.SilentSoundPlayer
import com.tdanylchuk.app.sound.SoundPlayer
import com.tdanylchuk.app.strategy.ContentStrategy
import java.io.Serializable

class Game(private val strategy: ContentStrategy) : Serializable, SwipeListener {

    private val field: Field = Field(strategy.getFieldHeight(), strategy.getFieldWidth())
    private val soundPlayer: SoundPlayer = SilentSoundPlayer()
    private var cellSize: Int = 50
    private var context: Context? = null
    private var finishCheckListener: AnimatorListener? = null

    fun init(parentLayout: FrameLayout) {
        cellSize = parentLayout.layoutParams.height / strategy.getFieldHeight()

        context = parentLayout.context
        finishCheckListener = FinishCheckingListener(field, context!!, strategy.getFinishImageId())

        initField(parentLayout)
    }

    private fun initField(parentLayout: FrameLayout) {
        val cellClickListener = CellClickListener(this)
        for (i in 0 until field.height) {
            for (j in 0 until field.width) {
                val cell = field.cell(i, j)
                val empty = i == field.height - 1 && j == field.width - 1
                cell.part = Part(i, j, createCellLayout(parentLayout, cell, empty, cellClickListener), empty)
            }
        }
    }

    private fun createCellLayout(parentLayout: FrameLayout, cell: Cell, empty: Boolean, cellClickListener: View.OnClickListener): View {
        val cellLayout = loadCell(parentLayout.context) as FrameLayout

        val params = FrameLayout.LayoutParams(cellSize, cellSize)
        params.setMargins(cell.x * cellSize, cell.y * cellSize, 0, 0)
        cellLayout.layoutParams = params

        val cellButton = cellLayout.findViewById<Button>(R.id.cell_button)

        cellButton.setOnClickListener(cellClickListener)
        strategy.applyViewContent(cellButton, cell, cellSize)

        cellLayout.visibility = if (empty) View.INVISIBLE else View.VISIBLE

        cellLayout.tag = cell

        parentLayout.addView(cellLayout, 0, params)
        return cellLayout
    }

    private fun loadCell(context: Context): View {
        val systemService = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return systemService.inflate(R.layout.cell_layout, null)
    }

    override fun onSwipeRight() {
        val emptyCell = field.getEmptyCell()
        if (emptyCell.x - 1 >= 0) {
            moveCell(field.cell(emptyCell.x - 1, emptyCell.y), emptyCell)
        }
    }

    override fun onSwipeLeft() {
        val emptyCell = field.getEmptyCell()
        if (emptyCell.x + 1 <= FIELD_CELLS_SIZE - 1) {
            moveCell(field.cell(emptyCell.x + 1, emptyCell.y), emptyCell)
        }
    }

    override fun onSwipeTop() {
        val emptyCell = field.getEmptyCell()
        if (emptyCell.y + 1 <= FIELD_CELLS_SIZE - 1) {
            moveCell(field.cell(emptyCell.x, emptyCell.y + 1), emptyCell)
        }
    }

    override fun onSwipeBottom() {
        val emptyCell = field.getEmptyCell()
        if (emptyCell.y - 1 >= 0) {
            moveCell(field.cell(emptyCell.x, emptyCell.y - 1), emptyCell)
        }
    }

    fun onClick(cell: Cell) {
        soundPlayer.play()
        try {
            if (cell.isNotEmpty()) {
                val x = cell.x
                val y = cell.y
                if (x > 0) {
                    tryMove(cell, x - 1, y)
                }
                if (x < FIELD_CELLS_SIZE - 1) {
                    tryMove(cell, x + 1, y)
                }
                if (y > 0) {
                    tryMove(cell, x, y - 1)
                }
                if (y < FIELD_CELLS_SIZE - 1) {
                    tryMove(cell, x, y + 1)
                }
            }
        } catch (e: Exception) {
        }
    }

    @Throws(Exception::class)
    private fun tryMove(cell: Cell, x: Int, y: Int) {
        val checking = field.cell(x, y)
        if (checking.isEmpty()) {
            moveCell(cell, checking)
            throw Exception()
        }
    }

    private fun moveCell(cellToMove: Cell, destinationCell: Cell) {
        val currentPart = cellToMove.part
        val currentView = currentPart!!.view

        val animX = ObjectAnimator.ofFloat(currentView, "x", (destinationCell.x * cellSize).toFloat())
        val animY = ObjectAnimator.ofFloat(currentView, "y", (destinationCell.y * cellSize).toFloat())

        val animator = createAnimator()
        animator.playTogether(animX, animY)
        animator.start()

        cellToMove.part = destinationCell.part
        destinationCell.part = currentPart

        currentView.tag = destinationCell
    }

    private fun createAnimator(): AnimatorSet {
        val animator = AnimatorSet()
        animator.duration = GameplayConstants.ANIMATION_SPEED.toLong()
        animator.addListener(finishCheckListener)
        return animator
    }

}
