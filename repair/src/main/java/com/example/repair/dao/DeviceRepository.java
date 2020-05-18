package com.example.repair.dao;

import com.example.repair.dao.bena.Device;
import com.example.repair.dao.bena.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {


}