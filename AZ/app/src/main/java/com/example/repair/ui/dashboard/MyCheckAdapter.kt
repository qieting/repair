package com.example.repair.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import kotlinx.android.synthetic.main.check.view.*

/**
 * @program: Repair
 *
 * @description: 点检适配
 *
 * @author: hsy
 *
 * @create: 2020-05-17 21:22
 **/
class MyCheckAdapter(var checks: MutableList<MyCheck>) : RecyclerView.Adapter<MyHoleder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.device, null
        )
        return MyHoleder(inflate);
    }

    override fun getItemCount(): Int {
        return checks.size
    }

    override fun onBindViewHolder(holder: MyHoleder, position: Int) {
        holder.bind(checks[position])
    }


}

class MyHoleder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(check: MyCheck) {
        itemView.iid.text = "id:${check.id.toString()}";
        itemView.user.text = "点检员:${check.user ?: "暂无"}"
        itemView.status.text = check.state
        itemView.device_id.text = check.device_id.toString()
    }
}