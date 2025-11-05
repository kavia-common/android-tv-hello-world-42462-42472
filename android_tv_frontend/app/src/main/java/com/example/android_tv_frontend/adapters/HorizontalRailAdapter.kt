package com.example.android_tv_frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tv_frontend.R
import com.example.android_tv_frontend.models.ContentItem

/**
 * PUBLIC_INTERFACE
 * HorizontalRailAdapter renders a horizontal list of TV cards using a provided item layout.
 * - Applies a focus scale effect for TV DPAD navigation.
 * - Binds image and title.
 * - Handles click events via a callback.
 */
class HorizontalRailAdapter(
    private val items: List<ContentItem>,
    @LayoutRes private val itemLayoutRes: Int = R.layout.item_card,
    private val onClick: (ContentItem) -> Unit
) : RecyclerView.Adapter<HorizontalRailAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.card_image)
        val title: TextView? = view.findViewById(R.id.card_title)
        init {
            // Focus scaling for TV
            view.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.animate().scaleX(1.08f).scaleY(1.08f).setDuration(90).start()
                    v.z = 3f
                } else {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(90).start()
                    v.z = 0f
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.imageRes)
        holder.title?.text = item.title

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
