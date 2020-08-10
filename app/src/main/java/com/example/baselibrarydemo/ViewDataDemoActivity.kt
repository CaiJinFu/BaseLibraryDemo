package com.example.baselibrarydemo

import androidx.lifecycle.Observer
import com.example.baselibrary.BaseViewDataActivity
import com.example.baselibrarydemo.databinding.ActivityViewdataBinding
import com.example.baselibrarydemo.lifecycle.TestViewDataViewModel

class ViewDataDemoActivity :
    BaseViewDataActivity<ActivityViewdataBinding, TestViewDataViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_viewdata

    override fun initData() {
        mViewModel.showMsg.observe(this, Observer { mDataBinding.user = it })
        mViewModel.showText("HelloWorld")
    }

    override fun showError(obj: Any?) {

    }
}