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
    var device_Id: Device,
    var part: String?,
    var comment: String?,
    var img: String?,
    var user: String?,

    var state: String,
    var downTime: String?,



    var bgTime: Date?,
    var spa: String?,
    var remark: String?,
    var rpImg: String?,
    var rpTime: String?,
    var fuser: String?,

    var verify: String?,
    var verify_date: Date?
)