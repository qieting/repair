package com.example.repair.data.model

import android.icu.text.CaseMap
import java.util.*

/**
 * @program: Repair
 *
 * @description: 消息通知
 *
 *
 * @create: 2020-05-17 18:45
 **/
data class Message(var id: Int, var title: String, var message: String, var gmtTime: Date? = null)