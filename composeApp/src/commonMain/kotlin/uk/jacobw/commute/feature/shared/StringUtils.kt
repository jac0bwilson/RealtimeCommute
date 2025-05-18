package uk.jacobw.commute.feature.shared

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun correctionString(
    intended: String,
    actual: String,
): AnnotatedString =
    buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Red, textDecoration = TextDecoration.LineThrough)) {
            append(intended.timestamp())
        }
        append(" ${actual.timestamp()}")
    }

fun String.timestamp(): String {
    if (this.all { char -> char.isDigit() } && this.length == 4) {
        return this.substring(0, 2) + ":" + this.substring(2)
    }

    return this
}
