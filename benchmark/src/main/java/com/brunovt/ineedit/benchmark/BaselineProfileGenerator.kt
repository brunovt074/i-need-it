package com.brunovt.ineedit.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.RequiresDevice
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RequiresDevice
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(packageName = "com.brunovt.ineedit") {
        pressHome()
        startActivityAndWait()

        device.wait(Until.hasObject(By.scrollable(true)), 5_000)

        val addButton = device.findObject(By.desc("Add"))
        addButton?.click()
        device.wait(Until.hasObject(By.text("Cancel")), 3_000)

        val cancelButton = device.findObject(By.text("Cancel"))
        cancelButton?.click()
        device.wait(Until.gone(By.text("Cancel")), 3_000)
    }
}
