package com.example.demo.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddrController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/first-url")
	public void getAddr() {
		
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/helloworld")
	public String getAddrString() {
		
		return "helloworld";
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/helloworld")
//	public String getAddrString(HttpServletResponse response) {
//		
//		try(PrintWriter writer = response.getWriter()){
//			writer.write("hello world");
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return "/helloworld";
//	}

	
}
