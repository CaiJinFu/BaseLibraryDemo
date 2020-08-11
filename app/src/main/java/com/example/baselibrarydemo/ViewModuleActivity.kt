package com.example.baselibrarydemo

import androidx.lifecycle.Observer
import com.example.baselibrary.BaseViewModelActivity
import com.example.baselibrary.utils.MLog
import com.example.baselibrarydemo.bean.ArticleData
import com.example.baselibrarydemo.lifecycle.TestViewModule
import kotlinx.android.synthetic.main.activity_viewmodel.*

class ViewModuleActivity : BaseViewModelActivity<TestViewModule>() {

    override val layoutId: Int
        get() = R.layout.activity_viewmodel

    override fun initData() {
//        mViewModel.showMsg.observe(this, Observer { tvViewModule.text = it })
//        mViewModel.showText("HelloWorld")
        mViewModel.getData(this)
        mViewModel.data.observe(this, Observer { showData(it) })
    }

    override fun showError(obj: Any?) {
        val errorMsg = obj as String
        MLog.i(logs = *arrayOf(errorMsg))
    }

    /**
     * 显示数据
     */
    private fun showData(data: ArticleData) {
        if (data.data.isEmpty()) return
        tvViewModule.text = "id: ${data.data[0].id} name ${data.data[0].name}"
    }

}