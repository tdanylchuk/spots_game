package com.tdanylchuk.app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView


class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        bindButtons()
        val imageResId = intent.getIntExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, -1)
        init(imageResId)
    }

    override fun onBackPressed() {
        backToStart()
    }

    private fun init(imageResId: Int) {
        val findViewById = findViewById<ImageView>(R.id.finish_image)
        findViewById.setImageResource(imageResId)
    }

    private fun bindButtons() {
        val button = findViewById<Button>(R.id.back_to_start)
        button.setOnClickListener { backToStart() }
    }

    private fun backToStart() {
        val intent = Intent(this, StartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}