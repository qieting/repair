package com.example.repair.controller;


import com.example.repair.dao.bena.Device;
import com.example.repair.dao.bena.MyCheck;
import com.example.repair.dao.bena.Repair;
import com.example.repair.dao.bena.User;
import com.example.repair.service.PeopleService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
public class MyController {

    @Autowired
    PeopleService peopleService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "正常使用";
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/people", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User people) {

        return peopleService.login(people);
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/peopl", method = RequestMethod.POST)
    public User addPeople(@RequestBody User people) {

        return peopleService.addUser(people);
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public List<User>  getUsers() {
        return peopleService.getUser();
    }



    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public Device  addDevice(@RequestParam("device")String device, @RequestParam(value = "file",required = false)MultipartFile file) {
   //     return peopleService.addDevice(device,file);
        System.out.println(1);
        Device device1 = new Gson().fromJson(device,Device.class);
        return peopleService.addDevice(device1,file);
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public List<Device>  getDevices() {
        return peopleService.getDevices();
    }


    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/mycheck", method = RequestMethod.GET)
    public List<MyCheck>  getChecks() {
        return peopleService.getCheck();
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/mycheck", method = RequestMethod.POST)
    public MyCheck  changeCheck(@RequestParam("check")String check, @RequestParam(value = "file",required = false)MultipartFile file) {
        //     return peopleService.addDevice(device,file);
        System.out.println(1);
        MyCheck myCheck =new Gson().fromJson(check,MyCheck.class);
        return peopleService.changeMyCheck(myCheck,file);
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/repair", method = RequestMethod.GET)
    public List<Repair>  getRepairs() {
        return peopleService.getRepairs();
    }

    // 返回-1代表账号不存在，返回-2代表密码错误，登陆成功则返回1
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public Repair  changeRepair(@RequestParam("repair")String check, @RequestParam(value = "file",required = false)MultipartFile file) {
        //     return peopleService.addDevice(device,file);
        System.out.println(1);
        Repair myCheck =new Gson().fromJson(check,Repair.class);
        return peopleService.changeRepari(myCheck,file);
    }

}
