package com.example.repair

import androidx.lifecycle.ViewModel
import com.example.repair.data.model.User

/**
 * @program: Repair
 *
 * @description: 用户共享模板
 *
 *
 * @create: 2020-05-16 21:14
 **/
class MyUser{

    companion object{
        @JvmStatic
        lateinit var user: User
        val  host ="http:192.168.2.102:8080/"
        var depts:MutableList<String> =ArrayList()
    }

}