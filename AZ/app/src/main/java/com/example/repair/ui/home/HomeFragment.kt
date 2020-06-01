package com.example.repair.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.data.model.Repair
import com.example.repair.ui.dashboard.DashboardViewModel
import com.example.repair.ui.dashboard.MyCheckAdapter
import com.example.repair.ui.login.LoginActivity
import com.example.repair.ui.notifications.DeviceAdapter
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var repairs: MutableList<Repair> = ArrayList<Repair>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =

            ViewModelProvider(this.activity!!.application as App).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //  val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        val recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        var sada =
            ViewModelProvider(activity!!.application as App).get(NotificationsViewModel::class.java)
        var sda =
            ViewModelProvider(activity!!.application as App).get(DashboardViewModel::class.java)
        val deviceAdapter = RepairAdapter(repairs,ArrayList<Device>(),ArrayList<MyCheck>());
        sada.devices.observe(viewLifecycleOwner, Observer {
            deviceAdapter.changeDevice(it)
        })
        val out = root.findViewById<Button>(R.id.out)
        out.setOnClickListener {
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
        val add_repair = root.findViewById<Button>(R.id.add_check)
        add_repair.setOnClickListener {
            var intent = Intent(context, AddRepair::class.java)
            startActivity(intent)
        }

        recyclerView.adapter = deviceAdapter
        homeViewModel.repairs.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                var repairsFinish: MutableList<Repair> = ArrayList<Repair>()
                var repairswait: MutableList<Repair> = ArrayList<Repair>()
                var repairsNoUser: MutableList<Repair> = ArrayList<Repair>()
                var repairsverify: MutableList<Repair> = ArrayList<Repair>()
                for (i in it) {
                    if (i.state.equals("待接单")) {
                        repairsNoUser.add(i)
                    } else
                        if (MyUser.user.type.equals("管理员") || i.user.equals(MyUser.user.name) || i.fuser!!.equals(
                                MyUser.user.name
                            )
                        ) {
                            if (i.state.equals("已完成")) {
                                repairsFinish.add(i)
                            } else if (i.state.equals("待验收")) {
                                repairsverify.add(i)
                            } else if (i.state.equals("待维修")) {
                                repairswait.add(i)
                            }
                        }
                }
                repairs.clear();
                if (MyUser.user.type.equals("管理员")) {
                    repairs.addAll(repairsverify)
                    repairs.addAll(repairswait)
                    repairs.addAll(repairsNoUser)
                    repairs.addAll(repairsFinish)
                } else {
                    repairs.addAll(repairsNoUser)
                    repairs.addAll(repairswait)
                    repairs.addAll(repairsverify)
                    repairs.addAll(repairsFinish)
                }
                deviceAdapter.notifyDataSetChanged()
            }
        })
        return root

    }
}
