package com.whitebeach.presentation.test

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

// 共通Rule
class FailureScreenshotRule : TestWatcher() {

    override fun failed(
        e: Throwable?,
        description: Description,
    ) {
        super.failed(e, description)

        val instrumentation = InstrumentationRegistry.getInstrumentation()

        val device = UiDevice.getInstance(instrumentation)

        val outputDirectory = getOutputDirectory()

        outputDirectory.mkdirs()

        val screenshotFile = File(
            outputDirectory,
            "${description.className}_${description.methodName}.png"
                .sanitizeFileName(),
        )

        device.takeScreenshot(screenshotFile)
    }

    private fun getOutputDirectory(): File {
        val arguments = InstrumentationRegistry.getArguments()

        val additionalOutputDir = arguments.getString("additionalTestOutputDir")

        return if (additionalOutputDir != null) {
            File(
                additionalOutputDir,
                "failure-screenshots",
            )
        } else {
            val context = InstrumentationRegistry.getInstrumentation().targetContext

            File(
                context.getExternalFilesDir(null),
                "failure-screenshots",
            )
        }
    }

    private fun String.sanitizeFileName(): String {
        return replace(
            Regex("[^A-Za-z0-9._-]"),
            "_",
        )
    }
}