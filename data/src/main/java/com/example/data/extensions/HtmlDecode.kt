package com.example.data.extensions

import androidx.core.text.HtmlCompat

fun String.decode(): String = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()