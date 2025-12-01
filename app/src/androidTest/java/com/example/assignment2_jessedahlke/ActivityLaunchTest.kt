package com.example.assignment2_jessedahlke

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
// Explicit UI Automator Imports
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.DefaultAsserter.assertTrue

@RunWith(AndroidJUnit4::class)
class ActivityLaunchTest {

    private lateinit var device: UiDevice
    private val PACKAGE_NAME = "com.example.assignment2_jessedahlke"
    // This is the app's label as it appears on the launcher.
    private val APP_NAME = "Assignment2 JesseDahlke"
    private val TIMEOUT = 5000L // 5 seconds timeout for waits

    // --- Setup: Get UiDevice and start from Home ---

    @Before
    fun startUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIMEOUT)
    }

    // --- Test Case: Launch App, Click Button, Verify Challenge ---

    @Test
    fun launchExplicitActivityAndVerifyChallenge() {
        // 1. Launch the app from the home screen by clicking the launcher icon

        // Find the app launcher icon by text, or fall back to description.
        var appLauncher: UiObject2? = device.findObject(By.text(APP_NAME))
        if (appLauncher == null) {
            appLauncher = device.findObject(By.desc(APP_NAME))
        }

        if (appLauncher != null) {
            // Click the icon and wait for the app to open
            appLauncher.clickAndWait(Until.newWindow(), TIMEOUT)
        } else {
            // Fallback: Launch via the package name intent (most reliable)
            val context = InstrumentationRegistry.getInstrumentation().context
            context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)?.let {
                it.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
            // Wait for the app to open
            device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT)
        }

        // Ensure we are inside the app before proceeding
        device.wait(Until.findObject(By.pkg(PACKAGE_NAME)), TIMEOUT)


        // 2. Click on the “Start Activity Explicitly” button
        val explicitButtonText = "Start Activity Explicitly"

        // Wait for the button to appear using the Until.hasObject() method.
        val buttonFound = device.wait(Until.hasObject(By.text(explicitButtonText)), TIMEOUT)
        assertTrue("The button '$explicitButtonText' was not found in MainActivity.", buttonFound)

        // Find the button again (now guaranteed to be there) and click it
        val explicitButton = device.findObject(By.text(explicitButtonText))
        explicitButton.click()

        // 3. Check that the resulting activity (SecondActivity) has the expected challenge text
        // Wait for the new activity content to stabilize
        device.wait(Until.findObject(By.pkg(PACKAGE_NAME)), TIMEOUT)

        // Verification text, using the exact string from your SecondActivity's challenges list.
        val expectedChallengeText = "1. Handling device fragmentation (different screens & OS versions)"

        // Wait for the challenge text to appear
        val textFound = device.wait(Until.hasObject(By.textContains(expectedChallengeText)), TIMEOUT)

        assertTrue("The SecondActivity did not display the required challenge text: '$expectedChallengeText'.",
            textFound)
    }
}