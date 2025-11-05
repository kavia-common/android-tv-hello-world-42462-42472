package com.example.android_tv_frontend

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/**
 * PUBLIC_INTERFACE
 * HelloWorldActivity shows the original simple "Hello World" page.
 * This is navigated to when the user selects "My Plan" from the Home screen.
 */
class HelloWorldActivity : FragmentActivity() {

    private lateinit var titleText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)

        titleText = findViewById(R.id.title_text)
        titleText.text = getString(R.string.hello_world)

        titleText.isFocusable = true
        titleText.isFocusableInTouchMode = true
        titleText.post { titleText.requestFocus() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                titleText.animate().alpha(0.85f).setDuration(80).withEndAction {
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
