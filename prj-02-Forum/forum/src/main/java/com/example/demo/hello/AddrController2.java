package com.example.demo.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddrController2 {
	
	@RequestMapping(value = "/hello-spring", method = RequestMethod.GET)
	public String getAddrStringRest() {
		return "hello spring";
	}
	
}
