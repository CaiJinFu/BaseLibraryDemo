# 基于Kotlin+MVVM+LiveData基类的封装

# 前言

Kotlin在国外已经非常流行了，好处不必多说了，如果不学习的话，那就只能落后了，那么迎娶白富美的机会可就少了许多。MVVM+LiveData也是目前比较流行的APP架构模式了，有必要学习一波。所以本着学习的态度，写了一个BaseModule，方便写新项目时可以直接依赖使用。废话不多说，开码。

## 思路
首先我的思路是这样的，有的Activity可能不需要ViewDataBinding，也有可能不需要ViewModel，也有可能都不需要，所以我的BaseActivity中，只加入了最基本的代码。然后有需要ViewDataBinding，那么大多数情况下都是需要ViewModel的，所以就有了BaseViewDataActivity，再者就是只有ViewModel的BaseViewModelActivity，这两个Activity都是继承于BaseActivity。如果有需要改的，可根据自己的需求自行修改。

### BaseActivity

建立一个单例ActivityTaskManager，管理Activity，依据今日头条适配方案，添加适配代码，一个显示加载中的dialog，具体可看代码

```java
package com.example.baselibrary

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.baselibrary.manager.ActivityTaskManager
import com.example.baselibrary.manager.ScreenManager.SCREEN
import com.example.baselibrary.utils.DialogLoadingUtils

/**
 * @name Android BaseLibrary
 * @class name：com.example.baselibrary
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
```

ActivityTaskManager，DialogLoadingUtils等都在我的工具类中，Kotlin写工具类还是比较轻松的。

### BaseViewModelActivity
```java
package com.example.baselibrary

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.baselibrary.lifecycle.BaseViewModel
import com.example.baselibrary.utils.MLog
import java.lang.reflect.ParameterizedType

/**
 * @name Android BaseLibrary
 * @class name：com.example.baselibrary
 * @class describe 带有ViewModel的BaseActivity
 * @author 猿小蔡
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
```
ViewModel对象必须通过ViewModelProviders.of(this).get(BaseViewModel::class.java)创建。有的人是在当前的Activity中自己实例化的，但是我觉得麻烦，所以这里就用反射创建出来了。具体的使用可以看ViewModuleActivity。

### BaseViewDataActivity

```java
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
            MLog.i(TAG, e.message)
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
```

ViewDataBinding需要通过DataBindingUtil.setContentView(this, layoutId)创建，layoutId就是你的布局id。布局最外层要layout，Demo里面有个例子ViewDataDemoActivity，布局activity_viewdata.xml。

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="user"
            type="com.example.baselibrarydemo.bean.User" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvName"
            android:layout_width="wrap_content"
            android:layout_height="50dp"
            android:text="@{user.name}" />

    </LinearLayout>

</layout>
```

具体的使用可以查看ViewDataDemoActivity，ViewModuleActivity。往后会继续加入网络请求

[项目地址](https://github.com/CaiJinFu/BaseLibraryDemo)

# 参考文章

[TODO-MVVM：使用JetPack中的AndroidX + ViewModel + LiveData + DataBinding组件，同时使用ViewPager2 + RxJava2 + Retrofit2 + Glide等主流框架进行搭建](https://github.com/azhon/TODO-MVVM)

[Android-Tools-boluomi：Android常用工具](https://github.com/geekDavid/Android-Tools-boluomi)

[使用LiveData和ViewModel为Android项目搭建MVVM架构（Kotlin语言版）（入门教程）](https://blog.csdn.net/weixin_44407870/article/details/85864927)
