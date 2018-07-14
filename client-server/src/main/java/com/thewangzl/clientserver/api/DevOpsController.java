package com.thewangzl.clientserver.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class DevOpsController {
	
	@RequestMapping("/userlist")
	public ResponseEntity<List<UserInfo>> getAllUsers(){
		return ResponseEntity.ok(getUsers());
	}
	
	
	private List<UserInfo> getUsers(){
		List<UserInfo> list = new ArrayList<>();
		list.add(new UserInfo("thewangzl", "thewangzl@gmail.com"));
		list.add(new UserInfo("the", "the@sina.com"));
		list.add(new UserInfo("wang", "wang@qq.com"));
		return list;
	}

}
