package com.example.repair.dao;

import com.example.repair.dao.bena.Repair;
import com.example.repair.dao.bena.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairRepository extends JpaRepository<Repair, Integer> {


}