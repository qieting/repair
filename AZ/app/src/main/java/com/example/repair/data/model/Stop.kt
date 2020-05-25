package com.example.repair.data.model

/**
 * @program: Repair
 *
 * @description: 设别停用信息表
 *
 *
 * @create: 2020-05-20 19:28
 **/
data class Stop(val id:Int ,val device:Int,val downTime:Int?=null,val comment:String)