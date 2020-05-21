package com.example.repair.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.data.model.Repair
import com.example.repair.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<Repair>>().apply {
        CoroutineScope(Dispatchers.Main).launch {

            var _li = withContext(Dispatchers.IO) {

                LoginDataSource().getRepairs()
            }

            value = _li ?: ArrayList()

        }
    }
    val repairs: LiveData<MutableList<Repair>> = _list


    fun add(user: Repair) {
        if (_list.value == null) {
            _list.value = ArrayList()
        }

        var lll = _list.value;
        lll!!.add(user)
        _list.value = lll
    }

    fun repalce(mycheck: Repair) {
        if (_list.value == null) {
            _list.value = ArrayList()
        }
        var lll = _list.value;
        for (i in lll!!) {
            if (i.id == mycheck.id) {
                lll.remove(i)
                lll.add(mycheck)
                break
            }
        }
        _list.value = lll
    }
}