package com.example.demo.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddrController3 {
	
	@GetMapping(value = "/hello-rest")
	public String getRestString() {
		return "hello rest";
	}
	
	@GetMapping(value = "/api/helloworld")
	public String gethelloWorldRest() {
		return "hello rest api";
	}
	
}
