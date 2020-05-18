package com.example.repair.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<MyCheck>>().apply {
        CoroutineScope(Dispatchers.Main).launch {

            var _li = withContext(Dispatchers.IO) {

                LoginDataSource().getChecks()
            }

            value = _li?:ArrayList<MyCheck>()

        }
    }
    val checks: LiveData<MutableList<MyCheck>> = _list
}