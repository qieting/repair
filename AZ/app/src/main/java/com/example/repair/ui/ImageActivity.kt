package com.example.repair.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.repair.R
import com.github.chrisbanes.photoview.PhotoViewAttacher
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity() {

    private var mAttacher: PhotoViewAttacher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val path =intent.getStringExtra("path")
        if(path!=null){
            mAttacher= PhotoViewAttacher(img)
            Glide.with(this).load(path)
                .into(img)
            //mAttacher!!.update()
        }

    }
}
