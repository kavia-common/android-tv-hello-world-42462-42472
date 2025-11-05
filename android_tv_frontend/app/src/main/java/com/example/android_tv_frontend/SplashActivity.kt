package com.example.android_tv_frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/**
 * PUBLIC_INTERFACE
 * SplashActivity is the launcher entry screen for the Android TV app.
 *
 * Shows a centered "HelloTV" text for approximately 3 seconds with Ocean Professional styling,
 * then navigates to MainActivity and finishes itself so the back button does not return here.
 *
 * Behavior:
 * - Displays a simple splash layout with large centered text.
 * - Uses a Handler to delay navigation for ~3 seconds.
 * - Finishes itself after starting MainActivity to prevent back navigation to splash.
 * - Landscape orientation is enforced via the manifest.
 */
class SplashActivity : FragmentActivity() {

    private val splashDelayMs: Long = 1500L
    private val handler = Handler(Looper.getMainLooper())

    private val navigateRunnable = Runnable {
        // Start MainActivity and finish splash so back won't return here
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
        // Optional: No transition to keep it minimal; could add overridePendingTransition(0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val title = findViewById<TextView>(R.id.splash_title)
        // Ensure focus for D-pad TVs (even though it's static text)
        title.isFocusable = true
        title.isFocusableInTouchMode = true
        title.post { title.requestFocus() }

        handler.postDelayed(navigateRunnable, splashDelayMs)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(navigateRunnable)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Optional subtle feedback if user presses OK during splash
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            val title = findViewById<TextView>(R.id.splash_title)
            title.animate().alpha(0.85f).setDuration(80).withEndAction {
                title.animate().alpha(1.0f).setDuration(80).start()
            }.start()
            return true
        }
        // Block BACK during splash to avoid exiting immediately
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
