package com.tdanylchuk.app.engine

import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.tdanylchuk.app.listener.FinishCheckingListener
import com.tdanylchuk.app.GameplayConstants
import com.tdanylchuk.app.R
import com.tdanylchuk.app.listener.CellClickListener
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.model.Field
import com.tdanylchuk.app.model.Part
import com.tdanylchuk.app.strategy.ContentStrategy
import java.io.Serializable

class Game(private val strategy: ContentStrategy) : Serializable {

    val field: Field = Field(strategy.getFieldHeight(), strategy.getFieldWidth())
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

    fun moveCell(cellToMove: Cell, destinationCell: Cell) {
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
        animator.duration = GameplayConstants.ANIMATION_SPEED
        animator.addListener(finishCheckListener)
        return animator
    }

}
