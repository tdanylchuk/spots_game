package com.tdanylchuk.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.engine.Game
import com.tdanylchuk.app.listener.FieldSwipeListener
import com.tdanylchuk.app.listener.GestureListener
import com.tdanylchuk.app.model.ContentType
import com.tdanylchuk.app.model.ContentType.ORIGINAL
import com.tdanylchuk.app.model.ContentType.PICTURE
import com.tdanylchuk.app.strategy.ContentStrategy
import com.tdanylchuk.app.strategy.OriginalStrategy
import com.tdanylchuk.app.strategy.PictureStrategy


class MainActivity : AbstractExitApprovalActivity() {

    private var gestureDetector: GestureDetector? = null

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
        gestureDetector = GestureDetector(this, GestureListener(25, 50, FieldSwipeListener(game)))
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        gestureDetector!!.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun getApprovalQuestion(): String {
        return "Press again if you want to exit current game."
    }

}
