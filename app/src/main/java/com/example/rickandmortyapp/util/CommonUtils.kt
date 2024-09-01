package com.example.rickandmortyapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val DEFAULT_VALUE = "unknown"
const val CHARACTER_ID = "CHARACTER_ID"
const val APP_TITLE = "https://static.tumblr.com/05d822c5649f0151506c1ecfbad8d956/cshfd3v/DYqnwespx/tumblr_static_tumblr_static_filename_focused_v3.jpg"
const val BG_IMAGE = "https://wallpapers.com/images/high/rick-sanchez-6eg36p2n6j7ipedn.webp"
private const val INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val OUTPUT_FORMAT = "dd MMMM yyyy"
private const val UTC = "UTC"

fun formatDate(timestamp: String): String {
    if (timestamp.isBlank()) return DEFAULT_VALUE
    val inputFormat = SimpleDateFormat(INPUT_FORMAT, Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone(UTC)

    val date: Date = inputFormat.parse(timestamp)!!

    val outputFormat = SimpleDateFormat(OUTPUT_FORMAT, Locale.getDefault())

    return outputFormat.format(date)
}
