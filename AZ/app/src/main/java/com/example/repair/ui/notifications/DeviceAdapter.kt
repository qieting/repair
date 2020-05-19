package com.example.repair.ui.notifications

import android.content.Intent
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.AddDeviceActivity
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.users.Users
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
class DeviceAdapter(var devices: MutableList<Device>) : RecyclerView.Adapter<MyHoleder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = from(parent?.context).inflate(
            R.layout.device, parent, false
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

    fun bind(device: Device) {
        itemView.iid.text = "id：${device.id}"
        itemView.name.text = "名称:${device.name}"
        itemView.dept.text = device.dept
        itemView.status.setOnClickListener {

            var intent: Intent = Intent(itemView.context, AddDeviceActivity::class.java)
            intent.putExtra("id", device.id);
            itemView.context.startActivity(intent)
        }
    }
}