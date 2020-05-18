package com.example.repair.dao;

import com.example.repair.dao.bena.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

   User findByMobile(String mobile);
}