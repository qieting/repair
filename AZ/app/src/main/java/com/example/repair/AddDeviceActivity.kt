package com.example.repair

import android.app.AlertDialog
import android.content.ContentUris
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.content_add_device.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AddDeviceActivity : AppCompatActivity() {
    val ALBUM = 2
    var message: String? = null;
    var device: Device? = null
    private lateinit var notificationsViewModel: NotificationsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)
        var id = intent.getIntExtra("id", 0);
        notificationsViewModel =
            ViewModelProvider(this.application as App).get(NotificationsViewModel::class.java)
        val ctype =
            arrayOf("A", "B", "C")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        spinner.setAdapter(adapter);

        var adapter1 =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MyUser.depts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        spinnerDept.setAdapter(adapter1);
        chooseImg.setOnClickListener {
            var intent: Intent = Intent()
            intent.setAction("android.intent.action.GET_CONTENT")
            intent.setType("image/*")
            startActivityForResult(intent, ALBUM)
        }
        for (i in notificationsViewModel.devices.value!!) {
            if (i.id == id) {
                device = i
                dept_text.setText(device!!.dept)
                loc.setText(device!!.loc)
                for (i in 0..2) {
                    if (device!!.type.equals(ctype[i])) {
                        spinner.setSelection(i)
                    }
                }
                type.setText(device!!.type)
                name.setText(device!!.name)
                dj.setText(device!!.dj)
                wh.setText(device!!.wh)
                if (device!!.img.equals("无")) {
                    device_img.setImageResource(R.drawable.device)
                } else
                    Glide.with(this).load("${MyUser.host}images/${device!!.id}\$${device!!.img}")
                        .into(device_img)
                chooseImg.setText("删除本设备")
                chooseImg.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("确认")
                    builder.setMessage("确认删除该设备吗")
                    builder.setPositiveButton("是", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            CoroutineScope(Dispatchers.Main).launch {

                                withContext(Dispatchers.IO) {

                                    LoginDataSource().deleteDevices(device!!.id)
                                }

                                ViewModelProvider(application as App).get(NotificationsViewModel::class.java)
                                    .remove(device!!.id)
                                finish()
                            }


                        }
                    })
                    builder.setNegativeButton("否", null)
                    builder.show()
                }

            }

        }
        if (MyUser.user.type.equals("管理员")) {
            fab.setOnClickListener {

                if (name.text.length == 0) {
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                } else {


                    CoroutineScope(Dispatchers.Main).launch {

                        if (device != null) {
                            device = Device(
                                id = device!!.id,
                                dept = spinnerDept.selectedItem.toString(),
                                loc = loc.text.toString(),
                                lv = spinner.selectedItem.toString(),
                                type = type.text.toString(),
                                name = name.text.toString(),
                                img = message ?: "无",
                                dj = dj.text.toString(),
                                wh = wh.text.toString()
                            )
                            withContext(Dispatchers.IO) {

                                LoginDataSource().addDevices(device!!)
                            }

                            ViewModelProvider(application as App).get(NotificationsViewModel::class.java)
                                .change(device!!)
                        } else {
                            device = Device(
                                dept = spinnerDept.selectedItem.toString(),
                                loc = loc.text.toString(),
                                lv = spinner.selectedItem.toString(),
                                type = type.text.toString(),
                                name = name.text.toString(),
                                img = message ?: "无",
                                dj = dj.text.toString(),
                                wh = wh.text.toString()
                            )
                            var _li = withContext(Dispatchers.IO) {


                                LoginDataSource().addDevices(device!!)
                            }

                            ViewModelProvider(application as App).get(NotificationsViewModel::class.java)
                                .add(
                                    _li
                                )

                        }
                        finish()


                    }


                }
            }
        } else {
            fab.visibility = GONE
            chooseImg.visibility = GONE
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
            .into(device_img)

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
