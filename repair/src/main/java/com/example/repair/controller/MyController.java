package com.example.repair.controller;


import com.example.repair.dao.bena.*;
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


    @RequestMapping(value = "/people", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User people) {

        return peopleService.login(people);
    }


    @RequestMapping(value = "/peopl", method = RequestMethod.POST)
    public User addPeople(@RequestBody User people) {

        return peopleService.addUser(people);
    }


    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public List<User> getUsers() {
        return peopleService.getUser();
    }


    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public Device addDevice(@RequestParam("device") String device, @RequestParam(value = "file", required = false) MultipartFile file) {
        //     return peopleService.addDevice(device,file);
        System.out.println(1);
        Device device1 = new Gson().fromJson(device, Device.class);
        return peopleService.addDevice(device1, file);
    }


    @RequestMapping(value = "/device", method = RequestMethod.GET)
    public List<Device> getDevices() {
        return peopleService.getDevices();
    }


    @RequestMapping(value = "/device", method = RequestMethod.DELETE)
    public void deleteDevices(Integer id) {
        peopleService.deleteDecvice(id);
    }


    @RequestMapping(value = "/message", method = RequestMethod.DELETE)
    public void deleteMessage(Integer id) {
        peopleService.deleteDecvice(id);
    }


    @RequestMapping(value = "/mycheck", method = RequestMethod.GET)
    public List<MyCheck> getChecks() {
        return peopleService.getCheck();
    }


    @RequestMapping(value = "/mycheck", method = RequestMethod.POST)
    public MyCheck changeCheck(@RequestParam("check") String check, @RequestParam(value = "file", required = false) MultipartFile file) {
        //     return peopleService.addDevice(device,file);
        System.out.println(1);
        MyCheck myCheck = new Gson().fromJson(check, MyCheck.class);
        return peopleService.changeMyCheck(myCheck, file);
    }


    @RequestMapping(value = "/repair", method = RequestMethod.GET)
    public List<Repair> getRepairs() {
        return peopleService.getRepairs();
    }


    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public Repair changeRepair(@RequestParam("repair") String check, @RequestParam(value = "file", required = false) MultipartFile file) {
        //     return peopleService.addDevice(device,file);
        System.out.println(1);
        Repair myCheck = new Gson().fromJson(check, Repair.class);
        return peopleService.changeRepari(myCheck, file);
    }


    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public List<Stop> getStops() {
        return peopleService.findStops();
    }

    @RequestMapping(value = "/dept", method = RequestMethod.POST)
    public Dept addPeople(@RequestBody Dept people) {

        return peopleService.addDepe(people);
    }

}
