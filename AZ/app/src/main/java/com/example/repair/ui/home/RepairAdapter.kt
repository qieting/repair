package com.example.repair.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.R
import com.example.repair.data.model.Repair
import kotlinx.android.synthetic.main.repair.view.*

/**
 * @program: Repair
 *
 * @description: 维修适配
 *
 * @author: hsy
 *
 * @create: 2020-05-18 13:41
 **/

class RepairAdapter(var devices: MutableList<Repair>) : RecyclerView.Adapter<MyHoleder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.repair, null
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
    }
}