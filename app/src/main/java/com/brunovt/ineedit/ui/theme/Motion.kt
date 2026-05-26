package com.brunovt.ineedit.ui.theme

import androidx.compose.animation.core.CubicBezierEasing

object Motion {
    val easeOut = CubicBezierEasing(0.2f, 0.7f, 0.3f, 1f)
    val easeSpring = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)
    val easeStandard = CubicBezierEasing(0.2f, 0f, 0f, 1f)

    const val fast = 140
    const val regular = 220
    const val slow = 380
}
