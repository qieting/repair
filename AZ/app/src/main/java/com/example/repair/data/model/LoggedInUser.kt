package com.example.repair.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val id: Int= 0,
    val name: String,
    val email: String,
    val mobile: String,
    val dept: String,
    var password:String?=null,
    val type: String  //类型，这里我们设定  a 管理员 b点检  c操作   d维修
 )
