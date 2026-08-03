package com.brunovt.ineedit.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brunovt.ineedit.ui.theme.LocalAppTokens

@Composable
fun BrandMark(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
) {
    val tokens = LocalAppTokens.current
    val needColor = tokens.needSolid
    val wantColor = tokens.wantSolid
    val wishColor = tokens.wishSolid

    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val barWidth = w * 0.1082f
        val radius = barWidth / 2f
        val gap = w * 0.0613f
        val bottom = h * 0.7778f

        val col1Left = w * 0.2764f
        val col2Left = col1Left + barWidth + gap
        val col3Left = col2Left + barWidth + gap

        val col1BarTop = h * 0.3629f
        drawRoundRect(
            color = needColor,
            topLeft = Offset(col1Left, col1BarTop),
            size = Size(barWidth, bottom - col1BarTop),
            cornerRadius = CornerRadius(radius, radius),
        )
        drawCircle(
            color = needColor,
            radius = radius,
            center = Offset(col1Left + radius, h * 0.2764f),
        )

        val col2BarTop = h * 0.408f
        drawRoundRect(
            color = wantColor,
            topLeft = Offset(col2Left, col2BarTop),
            size = Size(barWidth, bottom - col2BarTop),
            cornerRadius = CornerRadius(radius, radius),
        )

        val col3BarTop = h * 0.619f
        drawRoundRect(
            color = wishColor,
            topLeft = Offset(col3Left, col3BarTop),
            size = Size(barWidth, bottom - col3BarTop),
            cornerRadius = CornerRadius(radius, radius),
        )
        drawCircle(
            color = wishColor,
            radius = radius,
            center = Offset(col3Left + radius, h * 0.536f),
        )
    }
}
