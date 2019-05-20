package com.service;

import javax.xml.ws.Endpoint;

public class ServicePublisher {
	public static void main(String[] agrs) {
		Endpoint.publish("http://localhost:8080/SOAPWSDemo", new MyService());
	}

}
