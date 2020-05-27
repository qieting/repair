package com.example.repair.ui.dashboard

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
import com.example.repair.data.model.MyCheck
import kotlinx.android.synthetic.main.check.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @program: Repair
 *
 * @description: 点检适配
 *
 *
 * @create: 2020-05-17 21:22
 **/
class MyCheckAdapter(var checks: MutableList<MyCheck>) : RecyclerView.Adapter<MyHoleder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHoleder {
        val inflate = LayoutInflater.from(parent?.context).inflate(
            R.layout.check, parent, false
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
        var sss = (check.user?.toString() ?: "暂无")
        itemView.iid.text = "id:${check.id.toString()}";
        itemView.user.text = "点检员id：" + sss
        itemView.status.text = check.state
        itemView.device_id.text = "设备id:" + check.device_id.toString()
        itemView.status.setOnClickListener {
            if (MyUser.user.type.equals("管理员")) {
                if (check.state.equals("待检验")) {
                    var intent: Intent = Intent(itemView.context, AddCheck::class.java)
                    intent.putExtra("id", check.id)
                    itemView.context.startActivity(intent)
                }
            } else if (MyUser.user.type.equals("点检员")) {
                if (check.state.equals("待点检") && check.user == MyUser.user.name) {
                    var intent: Intent = Intent(itemView.context, AddCheck::class.java)
                    intent.putExtra("id", check.id)
                    itemView.context.startActivity(intent)
                } else if (check.state.equals("待接单")) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                    builder.setTitle("确认")
                    builder.setMessage("确认领取该点检单吗")
                    builder.setPositiveButton("是", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            CoroutineScope(Dispatchers.Main).launch {

                                val _li = withContext(Dispatchers.IO) {
                                    check.user = MyUser.user.name
                                    LoginDataSource().addCheck(check)
                                }

                                ViewModelProvider(itemView.context.applicationContext as App).get(
                                    DashboardViewModel::class.java
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