package com.example.repair.dao;

import com.example.repair.dao.bena.DeviceType;
import com.example.repair.dao.bena.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {


}