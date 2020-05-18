package com.example.repair.data.model

import java.util.*

/**
 * @program: Repair
 *
 * @description: 设备类
 *
 * @author: hsy
 *
 * @create: 2020-05-16 13:11
 **/
data class Device(
    var id: Int = 0,
    var name: String,
    var lv: String,
    var type: String,
    var dept: String,
    var loc: String,
    var img: String = "无",
    var gmtTime: Date? = null,
    var dj: String,
    var wh: String
)