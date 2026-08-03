package com.brunovt.ineedit.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollDashboard() = benchmarkRule.measureRepeated(
        packageName = "com.brunovt.ineedit",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.Full(),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
        },
    ) {
        device.wait(Until.hasObject(By.scrollable(true)), 3_000)
        val scrollable = device.findObject(By.scrollable(true))
        scrollable?.fling(androidx.test.uiautomator.Direction.DOWN)
        scrollable?.fling(androidx.test.uiautomator.Direction.UP)
    }
}
