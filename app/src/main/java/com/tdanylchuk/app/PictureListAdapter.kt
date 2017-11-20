package com.tdanylchuk.app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class PictureListAdapter(private val context: Activity,
                         private var pictures: Array<Int>) : BaseAdapter() {

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = pictures.size

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.picture_list_item, null, true)
        val imageView = rowView.findViewById<ImageView>(R.id.picture_image)
        imageView.setImageResource(pictures[position])
        return rowView
    }

}