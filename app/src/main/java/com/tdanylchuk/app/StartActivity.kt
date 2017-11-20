package com.tdanylchuk.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.tdanylchuk.app.IntentConstants.CONTENT_TYPE_PARAM_NAME
import com.tdanylchuk.app.model.ContentType
import com.tdanylchuk.app.model.ContentType.ORIGINAL
import com.tdanylchuk.app.model.ContentType.PICTURE

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        bindButtons()
    }

    private fun bindButtons() {
        bindButton(R.id.start_original_game_button, ORIGINAL, MainActivity::class.java)
        bindButton(R.id.start_picture_game_button, PICTURE, PictureListActivity::class.java)
    }

    private fun bindButton(buttonId: Int, contentType: ContentType, clazz: Class<*>) {
        val button = findViewById<Button>(buttonId)
        button.setOnClickListener {
            val intent = Intent(this, clazz)
            intent.putExtra(CONTENT_TYPE_PARAM_NAME, contentType.name)
            startActivity(intent)
        }
    }
}