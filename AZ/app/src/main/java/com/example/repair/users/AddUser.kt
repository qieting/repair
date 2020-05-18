package com.example.repair.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.repair.App
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.User
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_add_user.dept
import kotlinx.android.synthetic.main.activity_add_user.name
import kotlinx.android.synthetic.main.content_add_device.spinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        val ctype =
            arrayOf("管理员", "点检员", "维修员", "操作员")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        spinner.setAdapter(adapter);

        submit.setOnClickListener {
            if (name.text.length == 0) {
                Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            } else {


                CoroutineScope(Dispatchers.Main).launch {

                    var user = User(
                        name = name.text.toString(),
                        dept = dept.text.toString(),
                        email = email.text.toString(),
                        mobile = mobile.text.toString(),
                        password = password.text.toString(),
                        type = spinner.selectedItem.toString()
                    )
                    var _li = withContext(Dispatchers.IO) {

                        LoginDataSource().addUser(user)
                    }


                    ViewModelProvider(this as App).get(UserViewModel::class.java).users.value!!.add(
                        _li
                    )
                    finish()

                }


            }
        }


    }
}
