package com.example.repair.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.data.model.User
import kotlinx.android.synthetic.main.content_add_device.view.*
import kotlinx.android.synthetic.main.device.view.name
import kotlinx.android.synthetic.main.user.view.*
import kotlinx.android.synthetic.main.user.view.type

/**
 * @program: Repair
 *
 * @description: user的列表适配类
 *
 * @author: hsy
 *
 * @create: 2020-05-16 21:47
 **/
class UserAdapter(var devices: MutableList<User>) : RecyclerView.Adapter<MyHoleder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.user, null
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

    fun bind(device: User) {
        itemView.name.text = device.name
        itemView.iid.text = device.id.toString()
        itemView.type.text = device.type
        itemView.email.text = device.email
        itemView.phone.text = device.mobile

    }
}