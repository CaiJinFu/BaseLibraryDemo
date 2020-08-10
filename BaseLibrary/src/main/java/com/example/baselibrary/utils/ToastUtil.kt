package com.example.baselibrary.utils

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.baselibrary.utils.MainThreadUtil.post

/** 解决Toast重复弹出  */
object ToastUtil {
    private var application: Context? = null

    @JvmStatic
    fun setApplicationContext(application: Application?) {
        ToastUtil.application = application
    }

    @JvmStatic
    fun toast(message: String?) {
        toast(message, Toast.LENGTH_SHORT)
    }

    @JvmOverloads
    @JvmStatic
    fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (application == null) {
            throw KotlinNullPointerException("Application不能为空，请在Application中初始化ToastUtil，在ToastUtil的setApplicationContext方法中，传入Application")
        }
        val content = application!!.getString(resId)
        toast(content, duration)
    }

    @JvmStatic
    fun toast(content: String?, duration: Int) {
        if (application == null) {
            throw KotlinNullPointerException("Application不能为空，请在Application中初始化ToastUtil，在ToastUtil的setApplicationContext方法中，传入Application")
        }
        if (TextUtils.isEmpty(content)) {
            return
        }
        post(
            Runnable {
                val toast = Toast.makeText(application, content, duration)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            })
    }
}