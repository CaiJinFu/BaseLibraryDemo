package com.example.baselibrary.utils

import android.text.TextUtils
import android.util.Log
import com.example.baselibrary.BuildConfig

/** 日志工具类
 *
 * @author 猿小蔡
 */
object MLog {

    const val TAG: String = "TAG"

    @JvmStatic
    fun v(tag: String = TAG, vararg logs: String) {
        print(tag, LogType.VERBOSE, *logs)
    }

    @JvmStatic
    fun i(tag: String = TAG, vararg logs: String) {
        print(tag, LogType.INFO, *logs)
    }

    @JvmStatic
    fun d(tag: String = TAG, vararg logs: String) {
        if (BuildConfig.DEBUG) {
            print(tag, LogType.DEBUG, *logs)
        }
    }

    @JvmStatic
    fun w(tag: String = TAG, vararg logs: String) {
        print(tag, LogType.WARNING, *logs)
    }

    @JvmStatic
    fun e(tag: String = TAG, throwable: Throwable) {
        e(tag, throwable)
    }

    @JvmStatic
    fun e(tag: String = TAG, log: String?, throwable: Throwable?) {
        e(tag, log, throwable)
    }

    @JvmStatic
    fun e(tag: String = TAG, vararg logs: String) {
        print(tag, LogType.ERROR, *logs)
    }

    private fun print(
        tag: String,
        logType: LogType,
        vararg logs: String
    ) {
        if (logs.isEmpty() || TextUtils.isEmpty(tag)) {
            return
        }
        val builder = StringBuilder()
        for (log in logs) {
            builder.append(log)
        }
        when (logType) {
            LogType.VERBOSE -> Log.v(tag, builder.toString())
            LogType.INFO -> Log.i(tag, builder.toString())
            LogType.DEBUG -> Log.d(tag, builder.toString())
            LogType.WARNING -> Log.w(tag, builder.toString())
            LogType.ERROR -> Log.e(tag, builder.toString())
            else -> Log.v(tag, builder.toString())
        }
    }

    private enum class LogType {
        VERBOSE, INFO, DEBUG, WARNING, ERROR
    }
}