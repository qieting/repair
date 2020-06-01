package com.example.repair.ui.home

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
import android.webkit.RenderProcessGoneDetail
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.repair.App
import com.example.repair.MyUser
import com.example.repair.R
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Repair
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_add_repair.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AddRepair : AppCompatActivity() {
    val ALBUM = 2
    var message: String? = null;
    var repair: Repair? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repair)
        var iii = intent.getIntExtra("iii", 0);
        if(iii!=0){
            iid.setText(iii.toString())
        }

        val iiiii = intent.getIntExtra("id", 0);
        if (iiiii != 0) {
            val list =
                ViewModelProvider(application as App).get(HomeViewModel::class.java).repairs.value
            for (i in list!!) {
                if (i.id == iiiii) {
                    repair = i
                    iii=repair!!.device_Id
                    break
                }
            }
        }else{
            asdadsda.visibility= GONE
        }
        chooseImg.setOnClickListener {
            var intent: Intent = Intent()
            intent.setAction("android.intent.action.GET_CONTENT")
            intent.setType("image/*")
            startActivityForResult(intent, ALBUM)
        }
        val ctype =
            arrayOf("动力", "传动", "执行","控制")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        spinner.setAdapter(adapter);
        submit.setOnClickListener {
            var repair = Repair(
                comment = comment.text.toString(),
                img = message,
                part = spinner.selectedItem.toString(),
                user = MyUser.user.name,
                device_Id = iid.text.toString().toInt(),
                state = "待接单"
            )
            CoroutineScope(Dispatchers.Main).launch {

                val _li = withContext(Dispatchers.IO) {

                    LoginDataSource().addRepair(repair)

                }

                ViewModelProvider(application as App).get(HomeViewModel::class.java)
                    .add(_li)
                finish()
            }


        }
        if (repair != null) {

            val list =
                ViewModelProvider(application as App).get(NotificationsViewModel::class.java).devices.value!!
            for(iii in list){
                if(iii.id==repair!!.device_Id){
                    nname.setText(iii.name)
                    break
                }
            }

            iid.isEnabled=false
            comment.setText(repair!!.comment)
            comment.isEnabled = false
            spinner.visibility = GONE
            spinneraaa.setText("保修部位：     ${repair!!.part}")

            iid.setText(repair!!.device_Id.toString())
            Glide.with(this).load("${MyUser.host}images/${repair!!.id}@${repair!!.img}")
                .into(img)
            chooseImg.visibility = GONE
            submit.visibility = GONE
            val ctype =
                arrayOf("是", "否")
            var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
            spinner1.setAdapter(adapter);
            chooseImg1.setOnClickListener {
                var intent: Intent = Intent()
                intent.setAction("android.intent.action.GET_CONTENT")
                intent.setType("image/*")
                startActivityForResult(intent, ALBUM)
            }
            submit1.setOnClickListener {
                repair!!.spa = spinner1.selectedItem.toString()
                repair!!.rpImg = message
                repair!!.rpTime = rpTime.text.toString()
                repair!!.remark = fcomment.text.toString()
                repair!!.fuser == MyUser.user.name
                repair!!.state = "待验收"

                CoroutineScope(Dispatchers.Main).launch {

                    val _li = withContext(Dispatchers.IO) {

                        LoginDataSource().addRepair(repair!!)

                    }

                    ViewModelProvider(application as App).get(HomeViewModel::class.java)
                        .repalce(_li)
                    finish()
                }
            }

        } else {
            repairLayout.visibility = GONE
        }

        if(repair!=null&&MyUser.user.type.equals("管理员")){
            spinner1.visibility= GONE
            rpTime.setText("${repair!!.rpTime}分钟")
            rpTime.isEnabled=false
            fcomment.setText("${repair!!.remark}")
            fcomment.isEnabled=false
            chooseImg1.visibility= GONE
            dasdsada.setText("是否使用备件：${repair!!.spa}")
            Glide.with(this).load("${MyUser.host}images/${repair!!.id}@${repair!!.rpImg}")
                .into(img1)
            submit1.setText("检验完成")
            submit1.setOnClickListener {
                repair!!.verify=MyUser.user.name
                repair!!.state = "已完成"

                CoroutineScope(Dispatchers.Main).launch {

                    val _li = withContext(Dispatchers.IO) {

                        LoginDataSource().addRepair(repair!!)

                    }

                    ViewModelProvider(application as App).get(HomeViewModel::class.java)
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
        if (repair == null)
            Glide.with(this)
                .load(file).override(100, 100)
                //   .apply(mRequestOptions)
                .into(img)
        else {
            Glide.with(this)
                .load(file).override(100, 100)
                //   .apply(mRequestOptions)
                .into(img1)
        }

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
