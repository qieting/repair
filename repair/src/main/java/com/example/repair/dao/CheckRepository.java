package com.example.repair.dao;

import com.example.repair.dao.bena.DeviceType;
import com.example.repair.dao.bena.MyCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<MyCheck, Integer> {


}