package com.example.repair.dao;

import com.example.repair.dao.bena.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Integer> {

     Stop findByDeviceAndTimeIsNull(int device_id);
}