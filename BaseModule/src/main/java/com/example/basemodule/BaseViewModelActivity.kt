package com.example.basemodule

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.basemodule.lifecycle.BaseViewModel
import com.example.basemodule.utils.MLog
import java.lang.reflect.ParameterizedType

/**
 * @name Android BaseModule
 * @class name：com.example.basemodule
 * @class describe 带有ViewModel的BaseActivity
 * @anther jin
 * @time 2020/8/10
 * @change
 * @chang time
 */
abstract class BaseViewModelActivity<VModel : BaseViewModel> : BaseActivity() {
    private val TAG = javaClass.simpleName
    protected lateinit var mViewModel: VModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        mViewModel = initViewModel()
        initObserve()
        initData()
    }

    protected abstract fun initData()

    private fun initViewModel(): VModel {
        try {
            // 通过反射获取model的真实类型
            val pt =
                this.javaClass.genericSuperclass as ParameterizedType?
            val clazz =
                pt!!.actualTypeArguments[0] as Class<VModel>
            mViewModel = ViewModelProviders.of(this)[clazz]
        } catch (e: Exception) {
            MLog.e(TAG, e)
        }
        return mViewModel
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