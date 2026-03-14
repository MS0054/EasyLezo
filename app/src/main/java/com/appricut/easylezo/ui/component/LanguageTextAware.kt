package com.appricut.easylezo.ui.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.appricut.easylezo.ui.theme.iranSansFontFamily
import com.appricut.easylezo.ui.theme.quickSandFontFamily


fun String.isPersian(): Boolean {
    return this.any { it in '\u0600'..'\u06FF' || it in '\u0750'..'\u077F' }
}

fun AnnotatedString.isPersian(): Boolean {
    return this.any { it in '\u0600'..'\u06FF' || it in '\u0750'..'\u077F' }
}

@Composable
fun LanguageAwareText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null, // اگر خواستی override کنی
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    minLines: Int = 1,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: () -> Unit = {}
) {
    val isPersian = text.isPersian()
    val resolvedFontFamily =
        fontFamily ?: if (isPersian) iranSansFontFamily else quickSandFontFamily

    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = resolvedFontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        style = style,
        onTextLayout = onTextLayout
    )
}