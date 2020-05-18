package com.example.repair.dao;

import com.example.repair.dao.bena.Dept;
import com.example.repair.dao.bena.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository  extends JpaRepository<Dept, Integer> {


}