package com.example.baselibrarydemo.lifecycle

import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.basemodule.lifecycle.BaseViewModel

/**
 * @name BaseLibraryDemo
 * @class name：com.example.baselibrarydemo
 * @class describe
 * @anthor jin
 * @time 2020/8/10 12:09
 * @change
 * @chang time
 */

class TestViewModule : BaseViewModel() {

    val showMsg: MutableLiveData<String> = MutableLiveData()

    fun showText(
        text: String, owner: LifecycleOwner,
        observer: Observer<String>
    ) {
        showMsg.observe(owner, observer)
        mShowDialog.setValue(true)
        Handler().postDelayed({
            showMsg.postValue(text)
            mShowDialog.setValue(false)
        }, 1000)
    }
}