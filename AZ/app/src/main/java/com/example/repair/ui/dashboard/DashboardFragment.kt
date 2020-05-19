package com.example.repair.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var checks: MutableList<MyCheck> = ArrayList<MyCheck>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(activity!!.application as App).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        var add_check = root.findViewById<Button>(R.id.add_check)
        if (!MyUser.user.type.equals("管理员")) {
            add_check.visibility = GONE
        } else {
            add_check.setOnClickListener {
                var e = EditText(this.context)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this.activity)
                builder.setTitle("请输入点检设备id")
                builder.setView(e)
                builder.setPositiveButton("是", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {


                        var ID = e.text.toString().toInt()
                        val notificationsViewModel =
                            ViewModelProvider(this@DashboardFragment.activity!!.application as App).get(
                                NotificationsViewModel::class.java
                            )
                        var devices = notificationsViewModel.devices.value;
                        if (devices == null) {
                            Toast.makeText(
                                this@DashboardFragment.context,
                                "找不到该设备",
                                Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            var device: Device? = null;
                            for (i in devices) {
                                if (i.id == ID) {
                                    device = i;
                                    break
                                }
                            }
                            if (device != null) {
                                for (i in checks) {
                                    if (!i.state.equals("已完成") && i.device_id == device.id) {
                                        Toast.makeText(
                                            this@DashboardFragment.context,
                                            "该设备正在点检",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show();
                                        return
                                    }
                                }

                                CoroutineScope(Dispatchers.Main).launch {

                                    var _check = MyCheck(
                                        wh = device.wh,
                                        device_id = device.id,
                                        state = "待接单",
                                        dj = device.dj
                                    )
                                    var _li = withContext(Dispatchers.IO) {

                                        LoginDataSource().addCheck(_check)
                                    }
                                    dashboardViewModel.add(_li)
                                    Toast.makeText(
                                        this@DashboardFragment.context,
                                        "点检单上传成功",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show();
                                }

                            } else {
                                Toast.makeText(
                                    this@DashboardFragment.context,
                                    "找不到该设备",
                                    Toast.LENGTH_SHORT
                                )
                                    .show();
                            }
                        }

                    }
                })
                builder.setNegativeButton("否", null)
                builder.show()

            }
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        val deviceAdapter = MyCheckAdapter(checks);
        recyclerView.adapter = deviceAdapter
        dashboardViewModel.checks.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                var checksFinish: MutableList<MyCheck> = ArrayList<MyCheck>()
                var checkswait: MutableList<MyCheck> = ArrayList<MyCheck>()
                var checksNoUser: MutableList<MyCheck> = ArrayList<MyCheck>()
                var checksverify: MutableList<MyCheck> = ArrayList<MyCheck>()
                for (i in it) {
                    if (i.state.equals("待接单")) {
                        checksNoUser.add(i)
                    }else
                    if (MyUser.user.type.equals("管理员")||i.user!!.equals(MyUser.user.name)) {
                        if (i.state.equals("已完成")) {
                            checksFinish.add(i)
                        } else if (i.state.equals("待检验")) {
                            checksverify.add(i)
                        } else if (i.state.equals("待点检")) {
                            checkswait.add(i)
                        }
                    }
                }
                checks.clear();
                if (MyUser.user.type.equals("管理员")) {
                    checks.addAll(checksverify)
                    checks.addAll(checkswait)
                    checks.addAll(checksNoUser)
                    checks.addAll(checksFinish)
                } else {
                    checks.addAll(checksNoUser)
                    checks.addAll(checkswait)
                    checks.addAll(checksverify)
                    checks.addAll(checksFinish)
                }
                deviceAdapter.notifyDataSetChanged()
            }
        })
        return root
    }
}
