package com.example.repair.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.AddDeviceActivity
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.users.Users
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var devices: MutableList<Device> = ArrayList<Device>();
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this.requireActivity().application as App).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val addDevice = root.findViewById<Button>(R.id.add_device)
        val user_message =root.findViewById<Button>(R.id.user_message)
        val analyze =root.findViewById<Button>(R.id.analyze)
        if (!MyUser.user.type.equals("管理员")) {
            addDevice.visibility = GONE
            user_message.visibility = GONE
            analyze.visibility=GONE
        } else {
            addDevice.setOnClickListener {
                var intent: Intent = Intent(this.context, AddDeviceActivity::class.java)
                startActivity(intent)
            }
            user_message.setOnClickListener {
                var intent: Intent = Intent(this.context, Users::class.java)
                startActivity(intent)
            }
        }
        val recyclerView = root.findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        val deviceAdapter = DeviceAdapter(devices);
        recyclerView.adapter = deviceAdapter
        notificationsViewModel.devices.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size > 0) {
                devices.clear();
                devices.addAll(it)
                deviceAdapter.notifyDataSetChanged()
            }
        })
        return root
    }
}
