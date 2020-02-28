package com.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class SendMqMsgTest {

	@Test
	public void readFromPropTest(){
		SendMqMsg sendMq = new SendMqMsg();
		sendMq.readFromProps();
		assertNotNull(sendMq.getChannel());
		assertNotNull(sendMq.getHost());
		assertNotNull(sendMq.getManager());
		assertNotNull(sendMq.getQueueName());
	}
	
	@Test
	public void readFromFileTest() {
		SendMqMsg sendMq = new SendMqMsg();
		sendMq.readFromProps();
		assertNotNull(sendMq.readFromFile());
	}
	
	@Test
	public void readFromFileNegativeTest() {
		SendMqMsg sendMq = new SendMqMsg();
		sendMq.readFromProps();
		assertEquals("HelloWorld", sendMq.readFromFile());
	}
}
