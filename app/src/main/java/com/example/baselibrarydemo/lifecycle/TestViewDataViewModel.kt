package com.example.baselibrarydemo.lifecycle

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.baselibrary.lifecycle.BaseViewModel
import com.example.baselibrarydemo.bean.User

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
        text: String
    ) {
        mShowDialog.setValue(true)
        Handler().postDelayed({
            showMsg.postValue(User(text))
            mShowDialog.setValue(false)
        }, 1000)
    }
}