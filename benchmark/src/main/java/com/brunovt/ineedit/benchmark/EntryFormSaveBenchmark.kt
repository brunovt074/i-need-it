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
class EntryFormSaveBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun saveEntry() = benchmarkRule.measureRepeated(
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
        val addButton = device.findObject(By.desc("Add"))
        addButton?.click()
        device.wait(Until.hasObject(By.text("Name")), 3_000)

        val nameField = device.findObject(By.text("Name"))
        nameField?.click()
        device.findObject(By.focused(true))?.text = "Test item for benchmark"

        val saveButton = device.findObject(By.text("Save"))
        saveButton?.click()

        device.wait(Until.gone(By.text("Save")), 3_000)
    }
}
