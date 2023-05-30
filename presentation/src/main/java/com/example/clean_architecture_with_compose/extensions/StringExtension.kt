package com.example.clean_architecture_with_compose.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_DATE_TIME

@RequiresApi(Build.VERSION_CODES.O)
fun String.dateTime(): String {
    val outputFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    val dateTime = LocalDateTime.parse(this, ISO_DATE_TIME)

    return dateTime.format(outputFormat)
}

fun String.boldString(query: String): AnnotatedString {
    val text = this
    val annotatedString = buildAnnotatedString {
        text.split(query).forEachIndexed { index, piece ->
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                append(piece)
            }

            if (index < text.split(query).size - 1) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(query)
                }
            }
        }
    }
    return annotatedString
}

fun String.formatSecondsToMinutesAndSeconds(): String {
    val seconds = this.toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}