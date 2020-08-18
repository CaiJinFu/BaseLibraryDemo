package com.example.baselibrarydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.baselibrary.BaseActivity
import kotlinx.android.synthetic.main.activity_main.tvViewData
import kotlinx.android.synthetic.main.activity_main.tvViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class MainActivity : BaseActivity() {

  override val layoutId: Int
    get() = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)
    initData()
    runBlocking<Unit> {
      Log.d(TAG, "Calling foo....")
      val flow = foo()
      Log.d(TAG, "Calling collect...")
      flow.flowOn(Dispatchers.IO)
          .collect { value ->
            Log.d(TAG, "当前的线程名1：${Thread.currentThread().name}")
            Log.d(TAG, "onCreate: $value")
          }
      Log.d(TAG, "Calling collect again...")
      flow.collect { value ->
        Log.d(TAG, "当前的线程名2：${Thread.currentThread().name}")
        Log.d(TAG, "onCreate: $value")
      }
    }
    Log.d(TAG, "最后一行代码")
  }

  fun foo(): Flow<Int> = flow {
    Log.d(TAG, "当前的线程名3：${Thread.currentThread().name}")
    for (i in 1..3) {
      delay(100)
      emit(i)
    }
  }

  private val TAG = javaClass.simpleName

  fun initData() {
    tvViewData.setOnClickListener {
      startActivity(Intent(this, ViewDataDemoActivity::class.java))
    }
    tvViewModel.setOnClickListener {
      startActivity(Intent(this, ViewModuleActivity::class.java))
    }
  }

}