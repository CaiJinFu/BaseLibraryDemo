package com.example.baselibrarydemo.lifecycle

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.baselibrary.lifecycle.BaseViewModel
import com.example.baselibrarydemo.bean.ArticleData
import com.example.baselibrarydemo.net.Repository
import com.example.baselibrarydemo.utils.launch

/**
 * @name BaseLibraryDemo
 * @class name：com.example.baselibrarydemo
 * @class describe
 * @author 猿小蔡
 * @time 2020/8/10 12:09
 * @change
 * @chang time
 */

class TestViewModule : BaseViewModel() {

    val showMsg: MutableLiveData<String> = MutableLiveData()

    val data = MutableLiveData<ArticleData>()

    fun getData(context: Context?) = launch({
        mShowDialog.setValue(true)
        data.value = Repository.getWXArticle(context)
        mShowDialog.setValue(false)
    }, {
        mError.postValue("请求失败了")
        mShowDialog.setValue(false)
    })

    fun showText(
        text: String
    ) {
        mShowDialog.setValue(true)
        Handler().postDelayed({
            showMsg.postValue(text)
            mShowDialog.setValue(false)
        }, 1000)
    }
}