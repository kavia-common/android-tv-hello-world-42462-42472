package com.example.android_tv_frontend.models

import androidx.annotation.DrawableRes

/**
 * PUBLIC_INTERFACE
 * ContentItem represents a TV content/card to be shown in horizontal rails.
 *
 * @param title Display title for the card.
 * @param imageRes Drawable resource to use as the thumbnail.
 */
data class ContentItem(
    val title: String,
    @DrawableRes val imageRes: Int
)
