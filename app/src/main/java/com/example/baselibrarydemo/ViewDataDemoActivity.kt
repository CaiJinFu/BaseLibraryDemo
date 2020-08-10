package com.example.baselibrarydemo

import androidx.lifecycle.Observer
import com.example.baselibrarydemo.databinding.ActivityViewdataBinding
import com.example.baselibrarydemo.lifecycle.TestViewDataViewModel
import com.example.basemodule.BaseViewDataActivity

class ViewDataDemoActivity :
    BaseViewDataActivity<ActivityViewdataBinding, TestViewDataViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_viewdata

    override fun initData() {
        mViewModel.showText("HelloWorld", this, Observer { mDataBinding.user = it })
    }

    override fun showError(obj: Any?) {

    }
}