package com.example.repair.ui.notifications

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.R
import com.example.repair.data.model.Device
import kotlinx.android.synthetic.main.device.view.*

/**
 * @program: Repair
 *
 * @description: 设备列表展示信息
 *
 * @author: hsy
 *
 * @create: 2020-05-16 13:38
 **/
class DeviceAdapter(var devices:MutableList<Device>) :RecyclerView.Adapter<MyHoleder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = from(parent?.context).inflate(
            R.layout.device,null)
        return  MyHoleder(inflate);
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: MyHoleder, position: Int) {
       holder.bind(devices[position])
    }


}
class MyHoleder(itemView:View) :RecyclerView.ViewHolder(itemView){

    fun bind(device: Device){
        itemView.iid.text="id：${device.id}"
        itemView.name.text=device.name
        itemView.dept.text=device.dept
    }
}