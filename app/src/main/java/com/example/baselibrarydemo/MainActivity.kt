package com.example.baselibrarydemo

import android.content.Intent
import android.os.Bundle
import com.example.baselibrary.BaseActivity
import kotlinx.android.synthetic.main.activity_main.tvViewData
import kotlinx.android.synthetic.main.activity_main.tvViewModel

class MainActivity : BaseActivity() {

  override val layoutId: Int
    get() = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)
    initData()
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