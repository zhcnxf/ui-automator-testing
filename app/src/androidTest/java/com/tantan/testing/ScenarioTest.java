package com.tantan.testing;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Please Read
 * https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html
 * https://developer.android.com/training/testing/ui-automator.html
 */
@RunWith(AndroidJUnit4.class)
public class ScenarioTest {

    private static final String TANTAN_PACKAGE = "com.p1.mobile.putong";

    private UiDevice uiDevice;

    @Before
    public void startApp() throws Exception {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.pressHome();

        final String launcherPackage = uiDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        uiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 3000);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(TANTAN_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        uiDevice.wait(Until.hasObject(By.pkg(TANTAN_PACKAGE).depth(0)), 3000);
    }


    @Test
    public void testScenario() throws Exception {
        UiObject scenarioButton = uiDevice.findObject(new UiSelector().packageName(TANTAN_PACKAGE).resourceId("com.p1.mobile.putong:id/overlap"));
        assertTrue("Scenario should be enabled", scenarioButton.exists());

        scenarioButton.click();

        // Assertion here
        UiObject scenarioDialogTitle = uiDevice.findObject(new UiSelector().packageName(TANTAN_PACKAGE).text("What would you like to do?"));
        assertTrue("Scenario dialog should have a title", scenarioDialogTitle.waitForExists(3000));
    }

}