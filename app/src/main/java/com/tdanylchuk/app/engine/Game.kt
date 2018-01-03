package com.tdanylchuk.app.engine

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.tdanylchuk.app.FinishActivity
import com.tdanylchuk.app.IntentConstants
import com.tdanylchuk.app.R
import com.tdanylchuk.app.listener.CellClickListener
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.model.Field
import com.tdanylchuk.app.model.Part
import com.tdanylchuk.app.strategy.ContentStrategy
import java.io.Serializable

class Game(private val strategy: ContentStrategy) : Serializable, Finisher {

    private val field = Field(strategy.getFieldHeight(), strategy.getFieldWidth())
    private var context: Context? = null

    fun init(parentLayout: FrameLayout): FieldManipulator {
        context = parentLayout.context
        val manipulator = FieldManipulator(field, this)

        val cellSize = parentLayout.layoutParams.height / strategy.getFieldHeight()
        val cellClickListener = CellClickListener(manipulator)
        initField(parentLayout, cellClickListener, cellSize)

        manipulator.shuffle()
        return manipulator
    }

    private fun initField(parentLayout: FrameLayout, cellClickListener: CellClickListener, cellSize: Int) {
        for (i in 0 until field.height) {
            for (j in 0 until field.width) {
                val cell = field.cell(i, j)
                val empty = i == field.height - 1 && j == field.width - 1
                val cellView = createCellLayout(parentLayout, cell, empty, cellClickListener, cellSize)
                cell.part = Part(i, j, cellView, empty)
            }
        }
    }

    private fun createCellLayout(parentLayout: FrameLayout,
                                 cell: Cell,
                                 empty: Boolean,
                                 cellClickListener: View.OnClickListener,
                                 cellSize: Int): View {
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

    override fun tryFinish(): Boolean {
        if (field.correct()) {
            val intent = Intent(context, FinishActivity::class.java)
            intent.putExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, strategy.getFinishImageId())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            Log.d(javaClass.simpleName, "Staring finish Activity.")
            ContextCompat.startActivity(context, intent, null)
            return true
        }
        return false
    }


}
