package com.example.basemodule.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.basemodule.bean.DialogBean
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ViewModel基类，管理rxJava发出的请求，ViewModel销毁同时也取消请求
 *
 * @author 猿小蔡
 */
abstract class BaseViewModel : ViewModel() {
    /** 管理RxJava请求  */
    private var mCompositeDisposable: CompositeDisposable? = null

    /** 用来通知 Activity／Fragment 是否显示等待Dialog  */
    protected val  mShowDialog: DialogLiveData<DialogBean> = DialogLiveData()

    /** 当ViewModel层出现错误需要通知到Activity／Fragment  */
    protected val mError: MutableLiveData<Any> = MutableLiveData()

    /** 添加 rxJava 发出的请求  */
    protected fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    fun getShowDialog(
        owner: LifecycleOwner,
        observer: Observer<DialogBean>
    ) {
        mShowDialog.observe(owner, observer)
    }

    fun getError(
        owner: LifecycleOwner,
        observer: Observer<Any>
    ) {
        mError.observe(owner, observer)
    }

    /** ViewModel销毁同时也取消请求  */
    override fun onCleared() {
        super.onCleared()
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable = null
        }
    }
}