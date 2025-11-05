package com.example.android_tv_frontend

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.KeyEvent
import android.view.View
import android.widget.TextView

/**
 * Main Activity for Android TV.
 *
 * PUBLIC_INTERFACE
 * Displays a centered "Hello World" with Ocean Professional theme and ensures D-pad focus handling.
 */
class MainActivity : FragmentActivity() {

    private lateinit var titleText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById(R.id.title_text)
        titleText.text = "Hello World"

        // Request initial focus for D-pad navigation
        titleText.isFocusable = true
        titleText.isFocusableInTouchMode = true
        titleText.setOnFocusChangeListener { v, hasFocus ->
            // Optional: You can update styling on focus change if needed
            v.alpha = if (hasFocus) 1.0f else 0.95f
        }
        titleText.post { titleText.requestFocus() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Handle TV remote control inputs
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER -> {
                // Simple retro "blink" effect on OK
                titleText.animate().alpha(0.7f).setDuration(80).withEndAction {
                    titleText.animate().alpha(1.0f).setDuration(80).start()
                }.start()
                true
            }
            KeyEvent.KEYCODE_BACK -> {
                finish()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}
