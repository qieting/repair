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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.repair.App
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Device
import com.example.repair.data.model.MyCheck
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_add_check.*
import kotlinx.android.synthetic.main.check.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AddCheck : AppCompatActivity() {

    var message: String? = null;
    val ALBUM = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_check)
        chooseImg.setOnClickListener {
            var intent: Intent = Intent()
            intent.setAction("android.intent.action.GET_CONTENT")
            intent.setType("image/*")
            startActivityForResult(intent, ALBUM)
        }
        submit.setOnClickListener {
            var ID = device_id.text.toString().toInt()
            val notificationsViewModel =
                ViewModelProvider(application as App).get(NotificationsViewModel::class.java)
            var devices = notificationsViewModel.devices.value;
            if (devices == null) {
                Toast.makeText(this, "找不到该设备", Toast.LENGTH_SHORT).show();
            } else {
                var device: Device? = null;
                for (i in devices) {
                    if (i.id == ID) {
                        device = i;
                        break
                    }
                }
                if (device != null) {
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
                        ViewModelProvider(this as App).get(DashboardViewModel::class.java).checks.value!!.add(
                            _li
                        )
                        finish()

                    }


                } else {
                    Toast.makeText(this, "找不到该设备", Toast.LENGTH_SHORT).show();
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
