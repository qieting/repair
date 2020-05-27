package com.example.repair.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bz.view.*
import androidx.recyclerview.widget.RecyclerView

import com.example.repair.R
import com.example.repair.ui.login.afterTextChanged


/**
 * @program: Repair
 *
 * @description: z
 *
 *
 * @create: 2020-05-27 23:14
 **/
class BzAdapter(var checks: MutableList<String>) : RecyclerView.Adapter<MyHolder>() {

    var _enable = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.bz, parent, false
        )
        return MyHolder(inflate);
    }

    override fun getItemCount(): Int {
        return checks.size
    }

    fun setEnable(v: Boolean) {
        _enable = v
        notifyDataSetChanged()
    }

    fun getAll(): String {

        var ss = ""
        for (i in checks) {
            ss = ss + "#!@" + i;
        }
        return ss
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        if (_enable) {
            holder.itemView.edt.afterTextChanged {
                checks[position] = it
            }
            if (position == checks.size - 1) {
                holder.itemView.add.visibility = VISIBLE
                holder.itemView.add.setOnClickListener {
                    checks.add("")
                    notifyDataSetChanged()
                }
            } else {
                holder.itemView.add.visibility = GONE
                holder.itemView.edt.isEnabled = true
            }
        } else {
            holder.itemView.edt.setText(checks[position])
            holder.itemView.add.visibility = GONE
            holder.itemView.edt.isEnabled = false
        }

    }


}

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(check: Boolean, enable: Boolean) {
        if (enable) {
            if (check) {
                itemView.add.visibility = VISIBLE
                itemView.add.setOnClickListener {

                }
            } else {
                itemView.add.visibility = GONE
            }
        } else {
            itemView.add.visibility = GONE
            itemView.edt.isEnabled = false
        }

    }
}