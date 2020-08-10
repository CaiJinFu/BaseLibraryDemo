package com.example.baselibrarydemo

import android.app.Application
import com.example.baselibrary.utils.ToastUtil

/**
 * @name BaseLibraryDemo
 * @class name：com.example.baselibrarydemo
 * @class describe
 * @author 猿小蔡
 * @time 2020/8/10 13:50
 * @change
 * @chang time
 */

class BaseApplication : Application() {

    companion object {
        // app实例
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ToastUtil.setApplicationContext(this)
    }
}