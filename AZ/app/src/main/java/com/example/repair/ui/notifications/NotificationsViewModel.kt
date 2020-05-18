package com.example.repair.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.Result
import com.example.repair.data.model.Device
import com.example.repair.ui.login.LoggedInUserView
import com.example.repair.ui.login.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<Device>>().apply {
        CoroutineScope(Dispatchers.Main).launch {

            var _li = withContext(Dispatchers.IO) {

                LoginDataSource().getDevices()
            }

            value = _li?:ArrayList()

        }
    }
    val devices: LiveData<MutableList<Device>> = _list
}