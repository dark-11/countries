package com.example.countries

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import com.example.countries.utils.TestUtils.APP_PACKAGE_NAME
import com.example.countries.utils.TestUtils.getRowItem
import com.example.countries.utils.TestUtils.viewId
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CountryInstrumentTest {
    var mDevice: UiDevice? = null
    @Before
    fun setUp() {
        mDevice = UiDevice.getInstance(getInstrumentation())
        // Start from the home screen
        mDevice?.pressHome()

        // Wait for launcher
        val launcherPackage = launcherPackageName
        assertThat(launcherPackage, notNullValue())
        mDevice?.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT.toLong()
        )

        // Launch the blueprint app
        val context: Context = getApplicationContext()
        val intent: Intent? = context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)
        mDevice?.wait(
            Until.hasObject(By.pkg(APP_PACKAGE_NAME).depth(0)),
            LAUNCH_TIMEOUT.toLong()
        )
    }


    private val launcherPackageName: String
        private get() {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            val pm = getApplicationContext<Context>().packageManager
            val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return resolveInfo!!.activityInfo.packageName
        }

    @Test
    @Throws(Exception::class)
    fun checkRowItem() {
        val list = UiScrollable(UiSelector().resourceId(viewId("recyclerView")))
        var itemIndex = 0
        for (i in 0 until list.childCount) {
            val uiObject = list.getChild(getRowItem(itemIndex, "country"))
            val uiObject2 = list.getChild(getRowItem(itemIndex, "flag"))
            itemIndex++
            Assert.assertNotNull(uiObject.text)
            Assert.assertNotNull(uiObject2.text)
        }
    }
    @Test
    fun test_SmoothScrolling() {
        val itemScroll = UiScrollable(UiSelector().resourceId(viewId("recyclerView")).scrollable(true))
        itemScroll.scrollForward(200)
    }
    companion object {
        private const val LAUNCH_TIMEOUT = 8000
    }
}