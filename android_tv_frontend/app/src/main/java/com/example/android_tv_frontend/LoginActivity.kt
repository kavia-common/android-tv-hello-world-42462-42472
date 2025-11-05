package com.example.android_tv_frontend

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

/**
 * PUBLIC_INTERFACE
 * LoginActivity is a simple placeholder TV login screen with focusable controls.
 * This demonstrates DPAD navigation and click handling for preview builds.
 */
class LoginActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val title = findViewById<TextView>(R.id.login_title)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        listOf(title, btnLogin).forEach {
            it.isFocusable = true
            it.isFocusableInTouchMode = true
        }

        btnLogin.setOnClickListener {
            Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show()
        }

        title.post { title.requestFocus() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
