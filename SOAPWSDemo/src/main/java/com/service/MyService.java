package com.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT)
public class MyService {
	
	@WebMethod
	public String sayHello(String name) {
		return "Hello "+name;
	}

}
