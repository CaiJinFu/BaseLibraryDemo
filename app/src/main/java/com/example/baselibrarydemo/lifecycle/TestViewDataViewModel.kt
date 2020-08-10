package com.example.baselibrarydemo.lifecycle

import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.baselibrarydemo.bean.User
import com.example.basemodule.lifecycle.BaseViewModel

/**
 * @name BaseLibraryDemo
 * @class name：com.example.baselibrarydemo
 * @class describe
 * @author 猿小蔡
 * @time 2020/8/10 12:09
 * @change
 * @chang time
 */

class TestViewDataViewModel : BaseViewModel() {

    val showMsg: MutableLiveData<User> = MutableLiveData()

    fun showText(
        text: String, owner: LifecycleOwner,
        observer: Observer<User>
    ) {
        showMsg.observe(owner, observer)
        mShowDialog.setValue(true)
        Handler().postDelayed({
            showMsg.postValue(User(text))
            mShowDialog.setValue(false)
        }, 1000)
    }
}