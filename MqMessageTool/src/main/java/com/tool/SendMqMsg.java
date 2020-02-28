package com.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import com.ibm.msg.client.wmq.WMQConstants;

public class SendMqMsg {
	private static final Logger logger = Logger.getLogger(SendMqMsg.class);
	private String host;
	private int port;
	private String queueManager;
	private String channel;
	private String queueName;
	private String filePath;
	private String codePage;

	public static void main(String[] args) {
		SendMqMsg sendMqMsg = new SendMqMsg();
		sendMqMsg.readFromProps();
		sendMqMsg.send();
	}

	void send() {
		try {
			MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

			cf.setHostName(host);
			cf.setPort(port);
			cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
			cf.setQueueManager(queueManager);
			cf.setChannel(channel);

			Connection connection = cf.createConnection();

			MQQueueSession session = (MQQueueSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MQQueue queue = (MQQueue) session.createQueue("queue:///" + queueName);
			MQQueueSender sender = (MQQueueSender) session.createSender(queue);

			JMSTextMessage message = (JMSTextMessage) session.createTextMessage();
			message.setIntProperty(WMQConstants.JMS_IBM_CHARACTER_SET, 37);
			message.setText(readFromFile());
			connection.start();

			logger.log(Level.DEBUG, "before Sent message:\n" + message);

			sender.send(message);
			logger.log(Level.DEBUG, "Sent message:\n" + message);

			sender.close();

			session.close();
			connection.close();

			logger.log(Level.INFO, "\nSUCCESS\n");
		} catch (JMSException jmsex) {
			logger.log(Level.ERROR, jmsex);
			logger.log(Level.ERROR, "\nFAILURE\n");
			System.err.println(jmsex);
		} catch (Exception ex) {
			logger.log(Level.ERROR, ex);
			logger.log(Level.ERROR, "\nFAILURE\n");
		}
	}

	String readFromFile() {

		String st = null;
		Path path = Paths.get(filePath);
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			st = reader.readLine();
		} catch (IOException e) {
			logger.log(Level.ERROR, e);
		}

		return st;
	}

	void readFromProps() {
		try (InputStream input = new FileInputStream("config.properties")) {

			Properties prop = new Properties();
			prop.load(input);

			this.host = prop.getProperty("host");
			this.port = Integer.valueOf(prop.getProperty("port"));
			this.queueManager = prop.getProperty("queueManager");
			this.channel = prop.getProperty("channel");
			this.queueName = prop.getProperty("queueName");
			this.filePath = prop.getProperty("filePath");
			this.codePage = prop.getProperty("codePage");

		} catch (IOException ex) {
			logger.log(Level.ERROR, ex);
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getManager() {
		return queueManager;
	}

	public String getChannel() {
		return channel;
	}

	public String getQueueName() {
		return queueName;
	}

}
