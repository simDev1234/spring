package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
public class AddrController2 {
	
	@RequestMapping(value = "/hello-spring", method = RequestMethod.GET)
	public String getAddrStringRest() {
		return "hello spring";
	}
	
}
