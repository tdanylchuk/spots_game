package com.tdanylchuk.app

import android.view.View
import com.tdanylchuk.app.model.Cell
import com.tdanylchuk.app.model.Game

class CellClickListener(private val game: Game) : View.OnClickListener {

    override fun onClick(v: View?) {
        game.onClick((v!!.parent as View).tag as Cell)
    }

}
