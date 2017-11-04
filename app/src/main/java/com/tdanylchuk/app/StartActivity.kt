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
        bindButton(R.id.start_original_game_button, ORIGINAL)
        bindButton(R.id.start_picture_game_button, PICTURE)
    }

    private fun bindButton(resId: Int, contentType: ContentType) {
        val button = findViewById<Button>(resId)
        button.setOnClickListener { startGame(contentType) }
    }

    private fun startGame(contentType: ContentType) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(CONTENT_TYPE_PARAM_NAME, contentType.name)
        startActivity(intent)
    }
}