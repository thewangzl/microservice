package com.thewangzl.server.oauth2.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	/**
	 * 资源API
	 * @return
	 */
	@RequestMapping("/api/userinfo")
	public ResponseEntity<UserInfo> getUserInfo(){
		System.out.println(SecurityContextHolder.getContext()	//
				.getAuthentication().getPrincipal());
		User user = (User) SecurityContextHolder.getContext()	//
				.getAuthentication().getPrincipal();
		String email = user.getUsername() + "@gmail.com";
		
		UserInfo userInfo = new UserInfo();
		userInfo.setName(user.getUsername());
		userInfo.setEmail(email);
		
		return ResponseEntity.ok(userInfo);
	}
}
