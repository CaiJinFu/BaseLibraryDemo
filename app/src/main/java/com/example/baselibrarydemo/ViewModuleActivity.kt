package com.example.baselibrarydemo

import androidx.lifecycle.Observer
import com.example.baselibrarydemo.lifecycle.TestViewModule
import com.example.basemodule.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_viewmodel.*

class ViewModuleActivity : BaseViewModelActivity<TestViewModule>() {

    override val layoutId: Int
        get() = R.layout.activity_viewmodel

    override fun initData() {
        mViewModel.showText("HelloWorld", this, Observer { tvViewModule.text = it })
    }

    override fun showError(obj: Any?) {

    }
}