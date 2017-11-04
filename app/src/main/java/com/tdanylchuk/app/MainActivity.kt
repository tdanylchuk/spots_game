package com.tdanylchuk.app

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.model.ContentType
import com.tdanylchuk.app.model.ContentType.ORIGINAL
import com.tdanylchuk.app.model.ContentType.PICTURE
import com.tdanylchuk.app.model.Game
import com.tdanylchuk.app.strategy.ContentStrategy
import com.tdanylchuk.app.strategy.StrategyFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fieldLayout = findViewById<FrameLayout>(R.id.field)
        val contentType = ContentType.valueOf(intent.getStringExtra(IntentConstants.CONTENT_TYPE_PARAM_NAME))
        init(fieldLayout, contentType)
    }

    private fun init(fieldLayout: FrameLayout, contentType: ContentType) {
        val game = Game(FIELD_CELLS_SIZE, getStrategy(fieldLayout.context, contentType), R.drawable.pegiout)
        game.init(fieldLayout)
    }

    private fun getStrategy(context: Context, contentType: ContentType): ContentStrategy {
        return when (contentType) {
            ORIGINAL -> StrategyFactory.createOriginalStrategy()
            PICTURE -> StrategyFactory.createPictureStrategy(context, R.drawable.pegiout, FIELD_CELLS_SIZE)
        }
    }

}
