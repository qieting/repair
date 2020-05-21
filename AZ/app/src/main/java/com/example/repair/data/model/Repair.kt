package com.example.repair.data.model

import java.util.*

/**
 * @program: Repair
 *
 * @description: 报修
 *
 * @author: hsy
 *
 * @create: 2020-05-18 10:07
 **/

data class Repair(
    var id: Int = 0,
    var device_Id: Int,
    var part: String?,
    var comment: String?,
    var img: String?,
    var user: String?,

    var state: String,
    var downTime: String? = null,


    var bgTime: Date? = null,
    var spa: String? = null,
    var remark: String? = null,
    var rpImg: String? = null,
     var rpTime: String?=null,
    var fuser: String? = null,

    var verify: String? = null
    //var verify_date: Date?
)