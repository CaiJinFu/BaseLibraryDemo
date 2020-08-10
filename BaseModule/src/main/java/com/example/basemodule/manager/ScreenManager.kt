package com.example.basemodule.manager

import android.app.Activity
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * 适配设备屏幕尺寸
 * author：liu by 20200315
 */
object ScreenManager {

    private var sNoncompatDensity = 0f
    private var sNoncompatScaleDensity = 0f

    @JvmStatic
    fun SCREEN(activity: Activity?) {
        if (activity == null) {
            return
        }
        val application = activity.application
        val appDisplayMetrics = application.resources.displayMetrics
        if (sNoncompatDensity == 0f) {
            sNoncompatDensity = appDisplayMetrics.density
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        sNoncompatScaleDensity =
                            application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
            val targetDensity = appDisplayMetrics.widthPixels / 360f
            val targetScaledDensity =
                targetDensity * (sNoncompatScaleDensity / sNoncompatDensity)
            val targetDensityDpi = (160 * targetDensity).toInt()
            appDisplayMetrics.density = targetDensity
            appDisplayMetrics.scaledDensity = targetScaledDensity
            appDisplayMetrics.densityDpi = targetDensityDpi
            val displayMetrics = activity.resources.displayMetrics
            displayMetrics.density = targetDensity
            displayMetrics.scaledDensity = targetScaledDensity
            displayMetrics.densityDpi = targetDensityDpi
        }
    }
}