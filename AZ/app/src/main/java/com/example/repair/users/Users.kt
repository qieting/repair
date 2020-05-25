package com.example.repair.users

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Dept
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.data.model.User
import com.example.repair.ui.notifications.DeviceAdapter
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val intent = Intent(this, AddUser::class.java)
            startActivity(intent)
        }
        add_dept.setOnClickListener {
            var e = EditText(this)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("请输入部门名")
            builder.setView(e)
            builder.setPositiveButton("确认", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {


                    var name = e.text.toString()
                    if(!(name  in MyUser.depts))
                    CoroutineScope(Dispatchers.Main).launch {

                        var _check = Dept(name=name)
                        var _li = withContext(Dispatchers.IO) {

                            LoginDataSource().addDept(_check)
                        }
                        MyUser.depts.add(name)
                    }
                }
            })
            builder.setNegativeButton("否", null)
            builder.show()
        }

    }
}
