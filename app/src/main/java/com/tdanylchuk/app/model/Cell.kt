package com.tdanylchuk.app.model

import java.io.Serializable

class Cell(val x: Int, val y: Int) : Serializable {

    var part: Part? = null

    fun isEmpty(): Boolean = part!!.empty

    fun isNotEmpty(): Boolean = !part!!.empty

    fun containsProperPart(): Boolean = part!!.originX == x && part!!.originY == y

    override fun toString(): String = "Cell[ x[$x] - y[$y] - part[$part]]"
}
