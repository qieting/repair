package com.example.repair.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @program: Repair
 *
 * @description: 获取其他用户信息
 *
 * @author: hsy
 *
 * @create: 2020-05-16 21:48
 **/
class UserViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<User>>().apply {
        CoroutineScope(Dispatchers.Main).launch {

            var _li = withContext(Dispatchers.IO) {

                LoginDataSource().getUsers()
            }

            value = _li

        }
    }
    val users: LiveData<MutableList<User>> = _list
}