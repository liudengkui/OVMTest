package com.sino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sino.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
