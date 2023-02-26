package com.example.countries.utils

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import java.util.*


object TestUtils {
    const val APP_PACKAGE_NAME = "com.example.countries"

    var mDevice = UiDevice.getInstance(getInstrumentation())

    fun byId(id: String): UiObject {
        return mDevice.findObject(
            UiSelector()
                .resourceId(APP_PACKAGE_NAME + ":id/" + id)
        )
    }

    fun viewId(id: String): String {
        return APP_PACKAGE_NAME + ":id/" + id
    }

    @Throws(Exception::class)
    fun clickAndWait(id: String) {
        val cancelButton = byId(id)
        cancelButton.clickAndWaitForNewWindow()
    }

    fun resourceId(resourceId: String): String {
        return APP_PACKAGE_NAME + ":id/" + resourceId
    }

    fun getRowItem(itemIndex: Int, resourceId: String): UiSelector {
        return UiSelector()
            .resourceId(resourceId(resourceId))
            .enabled(true).instance(itemIndex)
    }

}