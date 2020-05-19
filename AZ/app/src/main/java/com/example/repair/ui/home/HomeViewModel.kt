package com.example.repair.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
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

            value = _li?:ArrayList()

        }
    }
    val repairs: LiveData<MutableList<Repair>> = _list


    fun add(user: Repair){
        _list.value!!.add(user)
        _list.value=_list.value
    }
}