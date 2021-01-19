package com.example.ulessondemo.util

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.ulessondemo.R

object Util {
    @DrawableRes
    private val SUBJECT_BG_DRAWABLES = arrayOf(
        R.drawable.ic_bg_orange,
        R.drawable.ic_bg_blue,
        R.drawable.ic_bg_yellow,
        R.drawable.ic_bg_green,
        R.drawable.ic_bg_purple
    )

    @DrawableRes
    private val PLAY_ICON_DRAWABLES = arrayOf(
        R.drawable.ic_play_orange,
        R.drawable.ic_play_blue,
        R.drawable.ic_play_yellow,
        R.drawable.ic_play_green,
        R.drawable.ic_play_purple
    )

    @ColorRes
    private val SUBJECT_TEXT_COLORS = arrayOf(
        R.color.subject_orange,
        R.color.subject_blue,
        R.color.subject_yellow,
        R.color.subject_green,
        R.color.subject_purple
    )

    @DrawableRes
    fun getBgDrawableForIndex(index: Int): Int {
        return SUBJECT_BG_DRAWABLES[index % SUBJECT_BG_DRAWABLES.size]
    }

    @DrawableRes
    fun getPlayDrawableForIndex(index: Int): Int {
        return PLAY_ICON_DRAWABLES[index % PLAY_ICON_DRAWABLES.size]
    }

    @ColorRes
    fun getColorForIndex(index: Int): Int {
        return SUBJECT_TEXT_COLORS[index % SUBJECT_TEXT_COLORS.size]
    }
}