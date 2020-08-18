package com.example.baselibrary.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.baselibrary.bean.DialogBean

/**
 * ViewModel基类，管理rxJava发出的请求，ViewModel销毁同时也取消请求
 *
 * @author 猿小蔡
 */
abstract class BaseViewModel : ViewModel() {

  /** 用来通知 Activity／Fragment 是否显示等待Dialog  */
  protected val mShowDialog: DialogLiveData<DialogBean> = DialogLiveData()

  /** 当ViewModel层出现错误需要通知到Activity／Fragment  */
  protected val mError: MutableLiveData<Any> = MutableLiveData()

  fun getShowDialog(
      owner: LifecycleOwner,
      observer: Observer<DialogBean>) {
    mShowDialog.observe(owner, observer)
  }

  fun getError(
      owner: LifecycleOwner,
      observer: Observer<Any>) {
    mError.observe(owner, observer)
  }

}