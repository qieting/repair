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

            value = _li ?: ArrayList<Device>()

        }
    }
    val devices: LiveData<MutableList<Device>> = _list

    fun remove(id: Int) {
        var lll = _list.value;
        for (i in lll!!) {
            if (i.id == id) {
                lll.remove(i)
                break
            }
        }
        _list.value = lll


    }

    fun change(device: Device) {
        var lll = _list.value;
        for (i in lll!!) {
            if (i.id == device.id) {
                lll.remove(i)
                lll.add(device)
                break
            }
        }
        _list.value = lll

    }

    fun add(device: Device) {
        var lll = _list.value;
        lll!!.add(device)
        _list.value = lll

    }
}