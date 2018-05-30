package com.sino.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sino.VO.ResultVO;
import com.sino.VO.ResultVOUtil;
import com.sino.model.User;
import com.sino.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/userlist")
	public ResultVO getUsers(){
		List<User> userlist = userRepository.findAll();
		
		return ResultVOUtil.success(userlist);
	}
	@RequestMapping("/user")
	public ResultVO getUser(){
		
		User user = userRepository.findOne(1l);
		
		return ResultVOUtil.success(user);
	}

}
