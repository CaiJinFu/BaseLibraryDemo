package com.example.basemodule

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.basemodule.manager.ActivityTaskManager
import com.example.basemodule.manager.ScreenManager.SCREEN
import com.example.basemodule.utils.DialogLoadingUtils

/**
 * @name Android BaseModule
 * @class name：com.example.basemodule
 * @describe Activity的基类，由于还需要给BaseViewDataActivity，BaseViewModelActivity继承，其中有不同的实现setContentView方式，
 * @describe 在BaseActivity中setContentView回导致重复加载，所以如果是继承BaseActivity，需要自己进行setContentView
 * @author 猿小蔡
 * @time 2020/8/10
 * @change
 * @chang time
 */
abstract class BaseActivity : AppCompatActivity() {
    private var mLoadingDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityTaskManager.instance.put(this)
        SCREEN(this)
    }

    @get:LayoutRes
    protected abstract val layoutId: Int

    /** 显示用户等待框  */
    protected fun showLoadingDialog(msg: String? = "") {
        mLoadingDialog = DialogLoadingUtils.createLoadingDialog(this, msg)
        mLoadingDialog!!.show()
    }

    /** 隐藏等待框  */
    protected fun dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityTaskManager.instance.remove(this)
    }

}