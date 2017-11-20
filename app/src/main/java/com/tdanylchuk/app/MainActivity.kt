package com.tdanylchuk.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.model.ContentType
import com.tdanylchuk.app.model.ContentType.ORIGINAL
import com.tdanylchuk.app.model.ContentType.PICTURE
import com.tdanylchuk.app.model.Game
import com.tdanylchuk.app.strategy.ContentStrategy
import com.tdanylchuk.app.strategy.OriginalStrategy
import com.tdanylchuk.app.strategy.PictureStrategy


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fieldLayout = findViewById<FrameLayout>(R.id.field)
        init(fieldLayout, intent)
    }

    private fun init(fieldLayout: FrameLayout, intent: Intent) {
        val strategy = getStrategy(fieldLayout.context, intent)
        val game = Game(strategy)
        game.init(fieldLayout)
    }

    private fun getStrategy(context: Context, intent: Intent): ContentStrategy {
        val contentType = ContentType.valueOf(intent.getStringExtra(IntentConstants.CONTENT_TYPE_PARAM_NAME))
        return when (contentType) {
            ORIGINAL -> OriginalStrategy(R.drawable.win, FIELD_CELLS_SIZE, FIELD_CELLS_SIZE)
            PICTURE -> {
                val imageResId = intent.getIntExtra(IntentConstants.IMAGE_RES_ID_PARAM_NAME, 0)
                return PictureStrategy(imageResId, FIELD_CELLS_SIZE, FIELD_CELLS_SIZE, context)
            }
        }
    }

}
