package com.example.repair.dao;

import com.example.repair.dao.bena.DeviceType;
import com.example.repair.dao.bena.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {


}