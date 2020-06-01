package com.example.repair.ui.dashboard

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.ui.ImageActivity
import com.example.repair.ui.home.AddRepair
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_add_check.*
import kotlinx.android.synthetic.main.activity_add_check.chooseImg
import kotlinx.android.synthetic.main.activity_add_check.spinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AddCheck : AppCompatActivity() {

    var message: String? = null;
    val ALBUM = 2
    lateinit var device: MyCheck

    var list: MutableList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_check)
        val id = intent.getIntExtra("id", 0)
        val notificationsViewModel =
            ViewModelProvider(application as App).get(DashboardViewModel::class.java)
        var devices = notificationsViewModel.checks.value;
        val recyclerView = findViewById<RecyclerView>(R.id.posts_recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val deviceAdapter = BzAdapter(list)
        recyclerView.adapter = deviceAdapter
        if (devices == null) {
            Toast.makeText(this, "找不到该点检信息", Toast.LENGTH_SHORT).show();
        } else {
            for (i in devices) {
                if (i.id == id) {
                    device = i;
                    break
                }
            }
            val notificationsViewModel =
                ViewModelProvider(this.application as App).get(NotificationsViewModel::class.java)
            for (i in notificationsViewModel.devices.value!!) {
                if (i.id == device.device_id) {
                    name.setText(i.name)
                    dj.setOnClickListener {
                        var intent: Intent = Intent(this, ImageActivity::class.java)
                        intent.putExtra("path", "${MyUser.host}images/${i!!.id}\$${i!!.dj}")
                        startActivity(intent)
                    }
                    wh.setOnClickListener {
                        var intent: Intent = Intent(this, ImageActivity::class.java)
                        intent.putExtra("path", "${MyUser.host}images/${i!!.id}\$${i!!.wh}")
                        startActivity(intent)
                    }
                    break
                }
            }


            chooseImg.setOnClickListener {
                var intent: Intent = Intent()
                intent.setAction("android.intent.action.GET_CONTENT")
                intent.setType("image/*")
                startActivityForResult(intent, ALBUM)
            }
            val ctype =
                arrayOf("正常", "异常", "停运")
            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
            spinner.setAdapter(adapter);
            submit.setOnClickListener {

                if (device != null) {

                    device.comment =
                        spinner.selectedItem.toString() + "$" + deviceAdapter.getAll()
                    device.img = message
                    device.state = "待检验"


                    CoroutineScope(Dispatchers.Main).launch {
                        var _li = withContext(Dispatchers.IO) {

                            LoginDataSource().addCheck(device)
                        }
                        ViewModelProvider(application as App).get(DashboardViewModel::class.java)
                            .repalce(_li)
                        if (!spinner.selectedItem.toString().equals("正常")) {
                            var intent = Intent(this@AddCheck, AddRepair::class.java)
                            intent.putExtra("iii",_li.device_id)
                            startActivityForResult(intent, 785)
                        } else
                            finish()


                    }

                } else {
                    Toast.makeText(this, "找不到该设备", Toast.LENGTH_SHORT).show();
                }
            }
        }
        list.add("")
        if (MyUser.user.type.equals("管理员")) {
            dj.visibility = GONE
            wh.visibility = GONE
            deviceAdapter.setEnable(false)
            val aaaaaaa = device.comment!!.split("$")[1].split("#!@")
            list.clear()
            for (i in aaaaaaa) {
                list.add(i)
            }
            list.removeAt(0)
            deviceAdapter.notifyDataSetChanged()
            result.setText("点检结果：${device.comment!!.split("$")[0]}")
            spinner.visibility = GONE
            Glide.with(this).load("${MyUser.host}images/${device!!.id}!${device!!.img}")
                .into(img)
            chooseImg.visibility = GONE
            submit.setText("审核")
            submit.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {

                    device.state = "已完成"
                    device.verify = MyUser.user.name
                    var _li = withContext(Dispatchers.IO) {

                        LoginDataSource().addCheck(device)
                    }
                    ViewModelProvider(application as App).get(DashboardViewModel::class.java)
                        .repalce(_li)
                    finish()
                }

            }
        }

    }


    //处理返回的事件
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ALBUM -> {
                if (resultCode == 0) {
                    Toast.makeText(baseContext, "未选择相应的图片", Toast.LENGTH_SHORT)
                } else
                    imageFromAlbum(data)
            }
            else -> {
                finish()
            }
        }
    }

    //获取从相册选择的图片
    fun imageFromAlbum(data: Intent?) {
        //如果安卓版本在4.4以上
        if (Build.VERSION.SDK_INT >= 19) {
            var imagePath: String? = null
            val uri: Uri? = data!!.data
            if (DocumentsContract.isDocumentUri(baseContext, uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                if ("com.android.providers.media.documents" == uri!!.getAuthority()) {
                    val id = docId.split(":").toTypedArray()[1]
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    imagePath =
                        getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
                } else if ("com.android.providers.downloads.documents" == uri.getAuthority()) {
                    val contentUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(docId)
                    )
                    imagePath = getImagePath(contentUri, null)
                }
            } else if ("content".equals(uri!!.getScheme(), ignoreCase = true)) {
                imagePath = getImagePath(uri, null)
            } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
                imagePath = uri.getPath()
            }
            message = imagePath
        } else {
            var uri = data!!.data;
            var path: String? = getImagePath(uri!!, null)
            message = path
        }
        val file = File(message)
        //            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                    .skipMemoryCache(true);//不做内存缓存
        Glide.with(this)
            .load(file).override(100, 100)
            //   .apply(mRequestOptions)
            .into(img)

    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor: Cursor? =
            baseContext.getContentResolver().query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst() /*判断是否为空*/) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}
