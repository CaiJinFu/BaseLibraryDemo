package com.example.baselibrary.manager

import android.app.Activity
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Activity栈管理类，当Activity被创建是压栈，销毁时出栈
 * @author 猿小蔡
 */
class ActivityTaskManager private constructor() {

    private val ACTIVITY_ARRAY =
        CopyOnWriteArrayList<Activity>()

    companion object {
        val instance: ActivityTaskManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityTaskManager()
        }
    }

    fun put(targetActivity: Activity) {
        var hasActivity = false
        for (activity in ACTIVITY_ARRAY) {
            if (targetActivity === activity) {
                hasActivity = true
                break
            }
        }
        if (!hasActivity) {
            ACTIVITY_ARRAY.add(targetActivity)
        }
    }

    fun remove(targetActivity: Activity?) {
        ACTIVITY_ARRAY.remove(targetActivity)
    }

    val topActivity: Activity?
        get() = if (ACTIVITY_ARRAY.isEmpty()) {
            null
        } else ACTIVITY_ARRAY[0]

    val lastActivity: Activity?
        get() = if (ACTIVITY_ARRAY.isEmpty()) {
            null
        } else ACTIVITY_ARRAY[ACTIVITY_ARRAY.size - 1]


}