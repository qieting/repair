package com.example.repair

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import cn.bmob.v3.Bmob

/**
 * @program: Repair
 *
 * @description: application类
 *
 *
 * @create: 2020-05-15 21:07
 **/
class App: Application() ,ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(baseContext,"32abf09bd212429ef4783ee908c8600e");
    }

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return  appViewModelStore
    }
}