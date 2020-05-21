package com.example.repair.service;

import com.example.repair.dao.bena.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

//这里没有做接口的分离，全部放在了这一个接口文档里
@RestController
public interface PeopleService {

    Map<String ,Object> login(User user);
    User addUser(User user);
    Device addDevice(Device device, MultipartFile file);
    List<Device> getDevices();
    List<User> getUser();
    void deleteDecvice(Integer id);
    void changeDevice(Device device);
    void deleteMessage(Integer id);

    List<MyCheck> getCheck();
    MyCheck changeMyCheck(MyCheck myCheck ,MultipartFile file);
    Repair changeRepari(Repair repair,MultipartFile file);
    List<Repair> getRepairs();

    List<Stop> findStops();

    List<Dept> getDepts();
    void addDepe(Dept depe);


}
