package com.example.repair.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.App
import com.example.repair.R
import com.example.repair.data.model.Device
import com.example.repair.data.model.User
import com.example.repair.ui.notifications.DeviceAdapter
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_users.*

class Users : AppCompatActivity() {

    private var devices: MutableList<User> = ArrayList<User>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        var notificationsViewModel =
            ViewModelProvider(this.application as App).get(UserViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val deviceAdapter = UserAdapter(devices);
        recyclerView.adapter = deviceAdapter
        notificationsViewModel.users.observe(this, Observer {
            if (it != null && it.size > 0) {
                devices.clear();
                devices.addAll(it)
                deviceAdapter.notifyDataSetChanged()
            }
        })
        add_people.setOnClickListener {
            val intent = Intent(this,AddUser::class.java)
            startActivity(intent)
        }

    }
}
