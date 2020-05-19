package com.example.repair.data.model

import java.util.*

/**
 * @program: Repair
 *
 * @description: 点检
 *
 * @author: hsy
 *
 * @create: 2020-05-17 18:00
 **/
data class MyCheck(
    var id: Int = 0,
    var dj: String,
    var wh: String,
    var device_id: Int,

    var comment: String? = null,
    var img: String? = null,
    var user: String? = null,


    var state: String,
    var verify: String? = null
    )