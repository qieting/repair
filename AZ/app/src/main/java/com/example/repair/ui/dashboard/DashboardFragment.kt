package com.example.repair.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.model.MyCheck
import com.example.repair.ui.notifications.DeviceAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var checks: MutableList<MyCheck> = ArrayList<MyCheck>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        var add_check =root.findViewById<Button>(R.id.add_check)
        if (!MyUser.user.type.equals("管理员")) {
            add_check.visibility = GONE
        } else {
            add_check.setOnClickListener {
                val intent = Intent(this.activity, AddCheck::class.java)
                startActivity(intent)

            }
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        val deviceAdapter = MyCheckAdapter(checks);
        recyclerView.adapter = deviceAdapter
        dashboardViewModel.checks.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                var checksFinish: MutableList<MyCheck> =ArrayList<MyCheck>()
                var checkswait: MutableList<MyCheck> =ArrayList<MyCheck>()
                var checksNoUser: MutableList<MyCheck> =ArrayList<MyCheck>()
                var checksverify: MutableList<MyCheck> =ArrayList<MyCheck>()
                for (i in it) {
                    if (i.state.equals("已完成")) {
                        checksFinish.add(i)
                    } else if (i.state.equals("待检验")) {
                        checksverify.add(i)
                    } else if (i.state.equals("待点检")) {
                        checkswait.add(i)
                    } else {
                        checksNoUser.add(i)
                    }
                }
                checks.clear();
                checks.addAll(checksverify)
                checks.addAll(checkswait)
                checks.addAll(checksNoUser)
                checks.addAll(checksFinish)
                deviceAdapter.notifyDataSetChanged()
            }
        })
        return root
    }
}
