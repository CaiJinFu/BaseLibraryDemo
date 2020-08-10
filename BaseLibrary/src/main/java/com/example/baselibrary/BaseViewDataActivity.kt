package com.example.baselibrary

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.baselibrary.lifecycle.BaseViewModel
import com.example.baselibrary.utils.MLog
import java.lang.reflect.ParameterizedType

/**
 * @name Android BaseLibrary
 * @class name：com.example.baselibrary
 * @class describe 带有ViewDataBinding与ViewModel的BaseActivity
 * @author 猿小蔡
 * @time 2020/8/10
 * @change
 * @chang time
 */
abstract class BaseViewDataActivity<DBinding : ViewDataBinding, VModel : BaseViewModel> :
    BaseActivity() {

    protected lateinit var mDataBinding: DBinding
    protected lateinit var mViewModel: VModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = initDataBinding()
        initData()
    }

    private val TAG = javaClass.simpleName

    protected abstract fun initData()
    private fun initViewModel(): VModel {
        try {
            // 通过反射获取model的真实类型
            val pt =
                this.javaClass.genericSuperclass as ParameterizedType?
            val clazz =
                pt!!.actualTypeArguments[1] as Class<VModel>
            mViewModel = ViewModelProviders.of(this)[clazz]
        } catch (e: Exception) {
            MLog.e(TAG, e.message, e)
        }
        return mViewModel
    }

    protected fun initDataBinding(): DBinding {
        mDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = initViewModel()
        initObserve()
        return mDataBinding
    }

    /** 监听当前ViewModel中 showDialog和error的值  */
    private fun initObserve() {
        mViewModel.getShowDialog(
            this,
            Observer { (isShow, msg) ->
                if (isShow) {
                    showLoadingDialog(msg)
                } else {
                    dismissLoadingDialog()
                }
            })
        mViewModel.getError(
            this,
            Observer { showError(it) })
    }


    /** ViewModel层发生了错误  */
    protected abstract fun showError(obj: Any?)

}