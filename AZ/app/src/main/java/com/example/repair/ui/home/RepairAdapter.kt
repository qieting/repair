package com.example.repair.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Repair
import com.example.repair.ui.dashboard.AddCheck
import com.example.repair.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.repair.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @program: Repair
 *
 * @description: 维修适配
 *
 *
 * @create: 2020-05-18 13:41
 **/

class RepairAdapter(var devices: MutableList<Repair>) : RecyclerView.Adapter<MyHoleder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.repair, parent, false
        )
        return MyHoleder(inflate);
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: MyHoleder, position: Int) {
        holder.bind(devices[position])
    }


}

class MyHoleder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(device: Repair) {
        itemView.iid.text = device.id.toString()
        itemView.device_id.text = "设备id:${device.device_Id}"
        itemView.status.text = device.state
        itemView.user.text = "上报人：${device.user}"
        itemView.part.text = device.part
        itemView.comment.text = device.comment
        itemView.status.setOnClickListener {
            if (MyUser.user.type.equals("管理员")) {
                if (device.state.equals("待验收")) {
                    var intent: Intent = Intent(itemView.context, AddRepair::class.java)
                    intent.putExtra("id", device.id)
                    itemView.context.startActivity(intent)
                }
            } else if (MyUser.user.type.equals("维修员")) {
                if (device.state.equals("待维修") && device.fuser == MyUser.user.name) {
                    var intent: Intent = Intent(itemView.context, AddRepair::class.java)
                    intent.putExtra("id", device.id)
                    itemView.context.startActivity(intent)
                } else if (device.state.equals("待接单")) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                    builder.setTitle("确认")
                    builder.setMessage("确认领取该维修单吗")
                    builder.setPositiveButton("是", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            CoroutineScope(Dispatchers.Main).launch {

                                val _li = withContext(Dispatchers.IO) {
                                    device.fuser = MyUser.user.name
                                    device.state="待维修"
                                    LoginDataSource().addRepair(device)
                                }

                                ViewModelProvider(itemView.context.applicationContext as App).get(
                                    HomeViewModel::class.java
                                )
                                    .repalce(_li)
                            }


                        }
                    })
                    builder.setNegativeButton("否", null)
                    builder.show()
                }
            }

        }

    }
}