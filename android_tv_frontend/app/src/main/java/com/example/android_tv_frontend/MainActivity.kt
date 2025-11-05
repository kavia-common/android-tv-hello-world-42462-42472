package com.example.android_tv_frontend

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tv_frontend.adapters.HorizontalRailAdapter
import com.example.android_tv_frontend.models.ContentItem

/**
 * PUBLIC_INTERFACE
 * MainActivity serves as the TV-style Home screen with:
 * - A top menu (Home, Login, Setting, My Plan) with DPAD focusable items.
 * - A hero banner image.
 * - Multiple horizontal content rails (Top Trending, Continue Watching, and genres).
 * - An "Available Subscriptions" rail.
 *
 * Navigation:
 * - Login -> LoginActivity
 * - My Plan -> HelloWorldActivity (existing Hello World page)
 *
 * Focus:
 * - All interactive elements are focusable and provide visual feedback.
 * - Initial focus lands on the "Home" top menu item.
 */
class MainActivity : FragmentActivity() {

    private lateinit var menuHome: TextView
    private lateinit var menuLogin: TextView
    private lateinit var menuSetting: TextView
    private lateinit var menuMyPlan: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var heroBanner: ImageView

    private lateinit var rvTrending: RecyclerView
    private lateinit var rvContinue: RecyclerView
    private lateinit var rvAction: RecyclerView
    private lateinit var rvDrama: RecyclerView
    private lateinit var rvHorror: RecyclerView
    private lateinit var rvOthers: RecyclerView
    private lateinit var rvSubscriptions: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindTopMenu()
        bindHero()
        bindRails()
        setupMenuActions()

        // Initial focus: put on Home menu for TV DPAD users
        menuHome.post { menuHome.requestFocus() }
    }

    private fun bindTopMenu() {
        scrollView = findViewById(R.id.home_scroll)
        menuHome = findViewById(R.id.menu_home)
        menuLogin = findViewById(R.id.menu_login)
        menuSetting = findViewById(R.id.menu_setting)
        menuMyPlan = findViewById(R.id.menu_my_plan)

        listOf(menuHome, menuLogin, menuSetting, menuMyPlan).forEach { applyFocusScale(it) }
    }

    private fun bindHero() {
        heroBanner = findViewById(R.id.hero_banner)
        heroBanner.setImageResource(R.drawable.hero_banner)
        applyFocusScale(heroBanner, scale = 1.02f) // allow subtle focus glow if needed
    }

    private fun bindRails() {
        rvTrending = findViewById(R.id.rv_trending)
        rvContinue = findViewById(R.id.rv_continue)
        rvAction = findViewById(R.id.rv_action)
        rvDrama = findViewById(R.id.rv_drama)
        rvHorror = findViewById(R.id.rv_horror)
        rvOthers = findViewById(R.id.rv_others)
        rvSubscriptions = findViewById(R.id.rv_subscriptions)

        setupHorizontal(rvTrending, makeSampleContent("Top", 12)) { item ->
            toastClick(item.title)
        }
        setupHorizontal(rvContinue, makeSampleContent("Continue", 10)) { item ->
            toastClick("Continue ${item.title}")
        }
        setupHorizontal(rvAction, makeSampleContent("Action", 10)) { item ->
            toastClick(item.title)
        }
        setupHorizontal(rvDrama, makeSampleContent("Drama", 10)) { item ->
            toastClick(item.title)
        }
        setupHorizontal(rvHorror, makeSampleContent("Horror", 10)) { item ->
            toastClick(item.title)
        }
        setupHorizontal(rvOthers, makeSampleContent("Others", 10)) { item ->
            toastClick(item.title)
        }
        setupHorizontal(
            rvSubscriptions,
            makeSubscriptions(),
            itemLayoutRes = R.layout.item_subscription
        ) { item ->
            toastClick("Subscribe: ${item.title}")
        }
    }

    private fun setupMenuActions() {
        menuHome.setOnClickListener {
            scrollView.smoothScrollTo(0, 0)
            toastClick(getString(R.string.menu_home))
        }
        menuLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        menuSetting.setOnClickListener {
            toastClick(getString(R.string.menu_setting))
        }
        menuMyPlan.setOnClickListener {
            // Navigate to existing Hello World page
            startActivity(Intent(this@MainActivity, HelloWorldActivity::class.java))
        }
    }

    private fun toastClick(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setupHorizontal(
        recyclerView: RecyclerView,
        items: List<ContentItem>,
        itemLayoutRes: Int = R.layout.item_card,
        onClick: (ContentItem) -> Unit
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = HorizontalRailAdapter(items, itemLayoutRes, onClick)
        recyclerView.setHasFixedSize(true)
        recyclerView.isFocusable = false // let child cards get focus
    }

    private fun applyFocusScale(view: View, scale: Float = 1.06f) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.animate().scaleX(scale).scaleY(scale).setDuration(90).start()
                v.z = 2f
            } else {
                v.animate().scaleX(1f).scaleY(1f).setDuration(90).start()
                v.z = 0f
            }
        }
    }

    private fun nextThumb(index: Int): Int {
        val thumbs = listOf(
            R.drawable.thumb_blue,
            R.drawable.thumb_amber,
            R.drawable.thumb_teal,
            R.drawable.thumb_red,
            R.drawable.thumb_purple,
            R.drawable.thumb_gray
        )
        return thumbs[index % thumbs.size]
    }

    private fun makeSampleContent(prefix: String, count: Int): List<ContentItem> {
        val items = mutableListOf<ContentItem>()
        repeat(count) { i ->
            items.add(
                ContentItem(
                    title = "$prefix ${i + 1}",
                    imageRes = nextThumb(i)
                )
            )
        }
        return items
    }

    private fun makeSubscriptions(): List<ContentItem> {
        return listOf(
            ContentItem("Basic Plan", R.drawable.thumb_teal),
            ContentItem("Standard Plan", R.drawable.thumb_blue),
            ContentItem("Premium Plan", R.drawable.thumb_amber),
            ContentItem("Sports Add-on", R.drawable.thumb_red),
            ContentItem("Kids Pack", R.drawable.thumb_purple)
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Keep BACK behavior consistent
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }
}
