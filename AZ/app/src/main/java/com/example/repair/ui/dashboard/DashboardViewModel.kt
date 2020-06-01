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


//    fun iii(){
//        if(_list!=null)
//        CoroutineScope(Dispatchers.Main).launch {
//
//            var _li = withContext(Dispatchers.IO) {
//
//                LoginDataSource().getChecks()
//            }
//
//            _li
//
//        }
//    }

    fun clear(){
        _list?.value?.clear()
    }

    private val _list = MutableLiveData<MutableList<MyCheck>>().apply {
        CoroutineScope(Dispatchers.Main).launch {

            var _li = withContext(Dispatchers.IO) {

                LoginDataSource().getChecks()
            }

            value = _li?:ArrayList<MyCheck>()
            if(value==null){
                value =ArrayList()
            }

        }
    }
    val checks: LiveData<MutableList<MyCheck>> = _list



    fun add(mycheck :MyCheck){
        if(_list.value==null){
           _list.value =ArrayList()
        }
        try {
            var lll = _list.value;
           lll!!.add(mycheck)
            _list.value = lll

        }catch (e:Throwable){
            print(1)
        }

    }

    fun repalce(mycheck: MyCheck){
        if(_list.value==null){
            _list.value =ArrayList()
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