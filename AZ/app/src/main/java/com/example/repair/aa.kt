package com.example.repair


import com.example.repair.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @program: Repair
 *
 * @description: 数据访问Retrofit
 *
 *
 * @create: 2020-05-15 21:35
 **/
interface AA {


    @POST("people")
    fun login(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("people")
    fun getUser(): Call<MutableList<User>>

    @POST("peopl")
    fun addPeople(@Body requestBody: RequestBody): Call<User>

    @GET("device")
    fun getDevices(): Call<MutableList<Device>>

    @Multipart
    @POST("device")
    fun addDevice(@Part file: List<MultipartBody.Part>): Call<Device>

    @Multipart
    @POST("device")
    fun addDevice(@Part("device") requestBody: RequestBody): Call<Device>

    @DELETE("device")
    fun deleteDevice(@Query("id") id: Int): Call<ResponseBody>


    @GET("mycheck")
    fun getChecks(): Call<MutableList<MyCheck>>

    @Multipart
    @POST("mycheck")
    fun addCheck(@Part("check") requestBody: RequestBody, @Part file: MultipartBody.Part): Call<MyCheck>

    @Multipart
    @POST("mycheck")
    fun changeCheck(@Part("check") requestBody: RequestBody): Call<MyCheck>


    @Multipart
    @POST("repair")
    fun addRepair(@Part("repair") requestBody: RequestBody, @Part file: MultipartBody.Part): Call<Repair>

    @Multipart
    @POST("repair")
    fun addRepair(@Part("repair") requestBody: RequestBody): Call<Repair>

    @GET("repair")
    fun getRepairs(): Call<MutableList<Repair>>

    @GET("stop")
    fun getStops(): Call<MutableList<Stop>>


    @POST("dept")
    fun adddEPT(@Body requestBody: RequestBody): Call<Dept>
}