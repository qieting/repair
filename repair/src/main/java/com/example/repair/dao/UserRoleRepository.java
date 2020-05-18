package com.example.repair.dao;

import com.example.repair.dao.bena.User;
import com.example.repair.dao.bena.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole,Integer> {


}