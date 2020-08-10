package com.example.baselibrarydemo

import android.app.Application
import com.example.basemodule.utils.ToastUtil

/**
 * @name BaseLibraryDemo
 * @class name：com.example.baselibrarydemo
 * @class describe
 * @anthor jin
 * @time 2020/8/10 13:50
 * @change
 * @chang time
 */

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtil.setApplicationContext(this)
    }
}