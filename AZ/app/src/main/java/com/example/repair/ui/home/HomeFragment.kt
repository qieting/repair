package com.example.repair.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.data.model.Repair
import com.example.repair.ui.notifications.DeviceAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var devices: MutableList<Repair> = ArrayList<Repair>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //  val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root

        val recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        val deviceAdapter = RepairAdapter(devices);
        recyclerView.adapter = deviceAdapter
        homeViewModel.repairs.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                var checksFinish: MutableList<Repair> = ArrayList<Repair>()
                var checkswait: MutableList<Repair> = ArrayList<Repair>()
                var checksNoUser: MutableList<Repair> = ArrayList<Repair>()
                var checksverify: MutableList<Repair> = ArrayList<Repair>()
                for (i in it) {
                    if (i.state.equals("已完成")) {
                        checksFinish.add(i)
                    } else if (i.state.equals("待检验")) {
                        checksverify.add(i)
                    } else if (i.state.equals("待接收")) {
                        checkswait.add(i)
                    } else {
                        checksNoUser.add(i)
                    }
                }
                devices.clear();
                devices.addAll(checksverify)
                devices.addAll(checkswait)
                devices.addAll(checksNoUser)
                devices.addAll(checksFinish)
                deviceAdapter.notifyDataSetChanged()
            }
        })

    }
}
