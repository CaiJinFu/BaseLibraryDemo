package com.example.baselibrary.utils

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @name BaseLibrary
 * @class name：com.example.baselibrary.utils
 * @class describe Toast扩展
 * @author 猿小蔡
 * @time 2020/8/10 11:58
 * @change
 * @chang time
 */
fun Activity.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.toast(resId, duration)
}

fun Activity.toast(message: String?) {
    ToastUtil.toast(message)
}

fun Activity.toast(content: String?, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.toast(content, duration)
}

fun Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.toast(resId, duration)
}

fun Fragment.toast(message: String?) {
    ToastUtil.toast(message)
}

fun Fragment.toast(content: String?, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.toast(content, duration)
}