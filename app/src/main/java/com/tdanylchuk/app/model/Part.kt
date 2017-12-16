package com.tdanylchuk.app.model

import android.view.View
import java.io.Serializable

class Part(val originX: Int,
           val originY: Int,
           val view: View,
           val empty: Boolean) : Serializable {

    override fun toString(): String =
            "Part[ originalX[$originX] - originalY[$originY]] - empty[$empty]"
}