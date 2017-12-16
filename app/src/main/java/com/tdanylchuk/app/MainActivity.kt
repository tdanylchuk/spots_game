package com.tdanylchuk.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.Toast
import com.tdanylchuk.app.GameplayConstants.FIELD_CELLS_SIZE
import com.tdanylchuk.app.model.ContentType
import com.tdanylchuk.app.model.ContentType.ORIGINAL
import com.tdanylchuk.app.model.ContentType.PICTURE
import com.tdanylchuk.app.model.Game
import com.tdanylchuk.app.strategy.ContentStrategy
import com.tdanylchuk.app.strategy.OriginalStrategy
import com.tdanylchuk.app.strategy.PictureStrategy


class MainActivity : AppCompatActivity(), SwipeListener {

    private var gestureDetector: GestureDetector? = null
    private var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this, GestureListener(25, 50, this))

        val fieldLayout = findViewById<FrameLayout>(R.id.field)
        init(fieldLayout, intent)
    }

    private fun init(fieldLayout: FrameLayout, intent: Intent) {
        val strategy = getStrategy(fieldLayout.context, intent)
        game = Game(strategy)
        game!!.init(fieldLayout)
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

    override fun onTouchEvent(event: MotionEvent): Boolean = gestureDetector!!.onTouchEvent(event)

    override fun onSwipeRight() {
        Toast.makeText(this, "RIGHT", Toast.LENGTH_SHORT).show()
        game!!.onSwipeRight()
    }

    override fun onSwipeLeft() {
        Toast.makeText(this, "LEFT", Toast.LENGTH_SHORT).show()
        game!!.onSwipeLeft()
    }

    override fun onSwipeTop() {
        Toast.makeText(this, "TOP", Toast.LENGTH_SHORT).show()
        game!!.onSwipeTop()
    }

    override fun onSwipeBottom() {
        Toast.makeText(this, "BOTTOM", Toast.LENGTH_SHORT).show()
        game!!.onSwipeBottom()
    }


}
