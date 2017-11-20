package com.tdanylchuk.app


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.tdanylchuk.app.model.ContentType


class PictureListActivity : AppCompatActivity() {

    var pictures = arrayOf<Int>(
            R.drawable.pinguin,
            R.drawable.pegiout,
            R.drawable.vinni,
            R.drawable.dino,
            R.drawable.pikachu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_list)

        val adapter = PictureListAdapter(this, pictures)
        val list = findViewById<ListView>(R.id.picture_list) as ListView
        list.adapter = adapter
        list.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@PictureListActivity, MainActivity::class.java)
            intent.putExtra(IntentConstants.CONTENT_TYPE_PARAM_NAME, ContentType.PICTURE.name)
            intent.putExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, pictures[position])
            startActivity(intent)
        }

    }
}
