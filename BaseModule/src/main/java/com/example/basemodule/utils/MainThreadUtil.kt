package com.example.basemodule.utils

import android.os.Handler
import android.os.Looper

/**
 * 主线程工具类
 * author：liu by 20200315
 */
object MainThreadUtil {
    /**
     * 主线程handler
     */
    private val MAIN_HANDLER = Handler(Looper.getMainLooper())

    /**
     * 发送消息
     *
     * @param callback 回调
     */
    @JvmStatic
    fun post(callback: Runnable?) {
        postDelayed(callback, 0)
    }

    /**
     * 发送延迟消息
     *
     * @param callback      回调
     * @param delayedMillis 延迟时间
     */
    @JvmStatic
    fun postDelayed(callback: Runnable?, delayedMillis: Long) {
        MAIN_HANDLER.postDelayed(callback, delayedMillis)
    }
}