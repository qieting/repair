package com.example.repair.data

import com.example.repair.AA
import com.example.repair.MyUser
import com.example.repair.data.model.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var retrofit = Retrofit.Builder().baseUrl("http://192.168.2.102:8080/").addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    fun login(username: String, password: String): Result<User> {

        var aa = retrofit.create(
            AA::class.java
        )
        lateinit var user: User
        var gson = Gson();
        var map = HashMap<String, String>();
        map.put("mobile", username);
        map.put("password", password);
        var jsonData = gson.toJson(map);

        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
        val reponseBody = aa.login(Mybody).execute();
        var body = reponseBody.body()?.string()
        if (body != null) {
            try {
                var a: JsonObject = Gson().fromJson(body, JsonObject::class.java);
                if (a["status"].asInt == 0) {

                } else {
                    user = Gson().fromJson(a["user"], User::class.java)
                    var deptsARRAY = a["dept"].asJsonArray
                    for (i in deptsARRAY) {
                        MyUser.depts.add(Gson().fromJson(i, Dept::class.java).name)
                    }
//
//                    val aaaa =
//                        object : TypeToken<List<Dept>>() {}.type
//                    var depts= Gson().fromJson(a["dept"], aaaa)
//                    print(depts)
//                    for (i in a["dept"] as JSONArray) {
//                        MyUser.depts.add(i.name)
//                    }
                    return Result.Success(user)
                }
            } catch (e: Throwable) {
                print(1)
            }

        }
        return Result.Error(IOException("Error logging in"))
    }

    fun getUsers(): MutableList<User> {
        var aa = retrofit.create(
            AA::class.java
        )
        var dd = aa.getUser().execute();
        return dd.body();

    }

    fun getDevices(): MutableList<Device> {
        var aa = retrofit.create(
            AA::class.java
        )
        var dd = aa.getDevices().execute();
        return dd.body();

    }

    fun deleteDevices(id: Int) {
        var aa = retrofit.create(
            AA::class.java
        )
        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"id\":${id}}")

        try {
            var dd = aa.deleteDevice(id).execute();
            var dda = dd.body()
        } catch (e: Throwable) {
            print(1)
        }

    }


    fun addUser(device: User): User {

//        var map =HashMap<String,RequestBody>()
//        map.put("file",File(device.img))
        var aa = retrofit.create(
            AA::class.java
        )

        var gson = Gson();
        var jsonData = gson.toJson(device);
        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)


        var dd = aa.addPeople(Mybody).execute();
        return dd.body()
    }

    fun addDevices(device: Device): Device {

//        var map =HashMap<String,RequestBody>()
//        map.put("file",File(device.img))
        var aa = retrofit.create(
            AA::class.java
        )
        if (device.img.equals("无") || device.id > 0) {
            var gson = Gson();
            var jsonData = gson.toJson(device);

            val Mybody: RequestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
            var dd = aa.addDevice(Mybody).execute();
            return dd.body()
        }
        val file = File(device.img)
        val dj = File(device.dj)
        val wh = File(device.wh)
        device.dj = dj.name
        device.wh = wh.name
        device.img = file.name

        var gson = Gson();
        var jsonData = gson.toJson(device);

//        val Mybody: RequestBody =
//            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)


        var builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM).addFormDataPart("device",jsonData)
        val imageBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val imageBody1 =
            RequestBody.create(MediaType.parse("multipart/form-data"), dj)
        val imageBody2 =
            RequestBody.create(MediaType.parse("multipart/form-data"), wh)
        builder.addFormDataPart("f1", file.name, imageBody)
        builder.addFormDataPart("f2", dj.name, imageBody1)
        builder.addFormDataPart("f3", wh.name, imageBody2)

        try {
            var dd = aa.addDevice( builder.build().parts()).execute();
            return dd.body()

        } catch (e: Throwable) {
            print(1)
        }
        var dd = aa.addDevice( builder.build().parts()).execute();
        return dd.body()
    }


    fun getChecks(): MutableList<MyCheck> {
        var aa = retrofit.create(
            AA::class.java
        )
        var dd = aa.getChecks().execute();
        return dd.body();

    }


    fun addCheck(check: MyCheck): MyCheck {

//        var map =HashMap<String,RequestBody>()
//        map.put("file",File(device.img))


        var aa = retrofit.create(
            AA::class.java
        )

        if (check.img != null && check.state.equals("待检验")) {
            val file = File(check.img)

            check.img = file.name

            var gson = Gson();
            var jsonData = gson.toJson(check);

            val Mybody: RequestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
            val fileRQ = RequestBody.create(MediaType.parse("image/*"), file)
            val part =
                MultipartBody.Part.createFormData("file", file.getName(), fileRQ)

            try {
                var dd = aa.addCheck(Mybody, part).execute();
                return dd.body()
            } catch (e: Throwable) {
                print(1)
            }
        } else {

        }

        var gson = Gson();
        var jsonData = gson.toJson(check);
        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)

        var dd = aa.changeCheck(Mybody).execute();
        return dd.body()


    }

    fun deleteMessage(id: Int) {
        var aa = retrofit.create(
            AA::class.java
        )
        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"id\":${id}}")

        try {
            var dd = aa.deleteDevice(id).execute();
            var dda = dd.body()
        } catch (e: Throwable) {
            print(1)
        }
    }


    fun getRepairs(): MutableList<Repair> {
        var aa = retrofit.create(
            AA::class.java
        )
        var dd = aa.getRepairs().execute();
        return dd.body();

    }

    fun getStop(): MutableList<Stop> {
        var aa = retrofit.create(
            AA::class.java
        )
        var dd = aa.getStops().execute();
        return dd.body();

    }


    fun addRepair(check: Repair): Repair {

//        var map =HashMap<String,RequestBody>()
//        map.put("file",File(device.img))
        var aa = retrofit.create(
            AA::class.java
        )
        if (check.img != null && check.state.equals("待接单")) {
            val file = File(check.img)

            check.img = file.name

            var gson = Gson();
            var jsonData = gson.toJson(check);

            val Mybody: RequestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
            val fileRQ = RequestBody.create(MediaType.parse("image/*"), file)
            val part =
                MultipartBody.Part.createFormData("file", file.getName(), fileRQ)

            try {
                var dd = aa.addRepair(Mybody, part).execute();
                return dd.body()
            } catch (e: Throwable) {
                print(1)
                var dd = aa.addRepair(Mybody, part).execute();
                return dd.body()
            }
        } else if (check.rpImg != null && check.state.equals("待验收")) {
            val file = File(check.rpImg)

            check.rpImg = file.name

            var gson = Gson();
            var jsonData = gson.toJson(check);

            val Mybody: RequestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
            val fileRQ = RequestBody.create(MediaType.parse("image/*"), file)
            val part =
                MultipartBody.Part.createFormData("file", file.getName(), fileRQ)

            try {
                var dd = aa.addRepair(Mybody, part).execute();
                return dd.body()
            } catch (e: Throwable) {
                print(1)
                var dd = aa.addRepair(Mybody, part).execute();
                return dd.body()
            }
        } else {
            var gson = Gson();
            var jsonData = gson.toJson(check);

            val Mybody: RequestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)
            try {
                var dd = aa.addRepair(Mybody).execute();
                return dd.body()
            } catch (e: Throwable) {
                print(1)
                var dd = aa.addRepair(Mybody).execute();
                return dd.body()
            }
        }

    }


    fun addDept(device: Dept): Dept {

//        var map =HashMap<String,RequestBody>()
//        map.put("file",File(device.img))
        var aa = retrofit.create(
            AA::class.java
        )

        var gson = Gson();
        var jsonData = gson.toJson(device);
        val Mybody: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)


        var dd = aa.adddEPT(Mybody).execute();
        return dd.body()
    }


    fun logout() {
        // TODO: revoke authentication
    }
}

