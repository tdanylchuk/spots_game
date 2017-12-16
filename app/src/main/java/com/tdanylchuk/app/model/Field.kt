package com.tdanylchuk.app.model

import java.io.Serializable

class Field(val height: Int,
            val width: Int) : Serializable {

    private var matrix: Array<Array<Cell>>

    init {
        matrix = Array(height, { x ->
            Array(width, { y -> Cell(x, y) })
        })
    }

    fun cell(x: Int, y: Int): Cell {
        return matrix[x][y]
    }

    fun correct(): Boolean {
        matrix.forEach { cells ->
            cells.filterNot { it.containsProperPart() }.forEach { return false }
        }
        return true
    }

    fun emptyCell(): Cell {
        matrix.forEach { cells ->
            cells.forEach {
                if (it.isEmpty()) {
                    return it
                }
            }
        }
        throw IllegalStateException("Field should have empty cell")
    }

}