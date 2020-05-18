package com.example.repair.service;

import com.example.repair.dao.*;
import com.example.repair.dao.bena.Device;
import com.example.repair.dao.bena.MyCheck;
import com.example.repair.dao.bena.Repair;
import com.example.repair.dao.bena.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Map<String, Object> login(User user) {

        Map<String, Object> map = new HashMap<>();
//        if(user.getName()!=null){
//            user =userRepository.save(user);
//            map.put("user",user);
//            return map;
//        }
        User user1 = userRepository.findByMobile(user.getMobile());
        if (user1 != null && user.getPassword().equals(user1.getPassword())) {
            map.put("status", 1);
            map.put("user", user1);
            map.put("device", getDevices());
        } else {
            map.put("status", 0);
        }
        return map;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Device addDevice(Device device, MultipartFile file) {

        device.setGmtTime(new Date());
        device = deviceRepository.save(device);
        if (file != null && !device.getImg().equals("无"))
            save(file, "images/" + device.getId() + "$" + file.getOriginalFilename());

        return device;
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public List<MyCheck> getCheck() {
        return checkRepository.findAll();
    }

    @Override
    public MyCheck changeMyCheck(MyCheck myCheck, MultipartFile file) {
        if (myCheck.getState().equals("待接单")) {
            return checkRepository.save(myCheck);
        } else {
            if (myCheck.getUser() == null) {
                myCheck.setState("待点检");
                return checkRepository.save(myCheck);
            } else if (myCheck.getTime() == null) {
                save(file, "images/" + myCheck.getId() + "!" + file.getOriginalFilename());
                myCheck.setTime(new Date());
                return checkRepository.save(myCheck);
            } else {
                myCheck.setVerify_date(new Date());
                return checkRepository.save(myCheck);
            }

        }
    }

    @Override
    public Repair changeRepari(Repair repair, MultipartFile file) {
        if (repair.getUser() == null) {
            repair.setState("待接收");
            save(file, "images/" + repair.getId() + "@" + file.getOriginalFilename());
            return repairRepository.save(repair);
        } else if (repair.getBgTime() == null) {
            repair.setState("待维修");
            return repairRepository.save(repair);
        } else if (repair.getVerify() == null) {
            repair.setState("待验收");
            save(file, "images/" + repair.getId() + "@" + file.getOriginalFilename());
            return repairRepository.save(repair);
        } else {
            repair.setState("已完成");
            return repairRepository.save(repair);
        }
    }

    @Override
    public List<Repair> getRepairs() {
        return repairRepository.findAll();
    }

    public void save(MultipartFile file, String path) {
        if (file.isEmpty()) {
            System.out.println("上传文件为空");
            return;
        }
        //加个时间戳，尽量避免文件名称重复
        path = "E:/repair/" + path;

        //创建文件路径
        File dest = new File(path);

        //判断文件是否已经存在
        if (dest.exists()) {
            System.out.println("文件已经存在" + path);
            return;
        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            //上传文件
            dest.createNewFile();
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
