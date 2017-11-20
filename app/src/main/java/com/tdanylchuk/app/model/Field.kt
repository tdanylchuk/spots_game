package com.tdanylchuk.app.model

import java.io.Serializable

class Field(val height: Int,
            val width: Int) : Serializable {

    private var matrix: Array<Array<Cell>>

    init {
        matrix = Array<Array<Cell>>(height, { x ->
            Array<Cell>(width, { y -> Cell(x, y) })
        })
    }

    fun cell(x: Int, y: Int): Cell {
        return matrix[x][y]
    }

    fun correct(): Boolean {
        matrix.forEach { cells -> cells.filterNot { it.containsProperPart() }.forEach { return false } }
        return true
    }


}