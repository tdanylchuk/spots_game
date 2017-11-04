package com.tdanylchuk.app.model

import java.io.Serializable

class Cell(val x: Int, val y: Int) : Serializable {

    var part: Part? = null

    fun isEmpty(): Boolean {
        return part!!.empty
    }

    fun isNotEmpty(): Boolean {
        return !part!!.empty
    }

    fun containsProperPart(): Boolean {
        return part!!.originX == x && part!!.originY == y
    }

    override fun toString(): String {
        return "Cell[ x[$x] - y[$y] - part[$part]]"
    }
}
