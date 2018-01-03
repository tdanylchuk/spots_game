package com.tdanylchuk.app.listener

import android.view.View
import com.tdanylchuk.app.engine.FieldManipulator
import com.tdanylchuk.app.model.Cell

class CellClickListener(private val manipulator: FieldManipulator) : View.OnClickListener {

    override fun onClick(v: View?) {
        manipulator.tryMoveCell((v!!.parent as View).tag as Cell)
    }
}
