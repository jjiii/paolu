package com.fc.pay.shop.controller;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Shop {
	public static void main(String[] args) throws Exception{

		// TODO Auto-generated method stub
		// Create a basic jetty server object that will listen on port 8080.
		// Note that if you set this to port 0 then
		// a randomly available port will be assigned that you can either look
		// in the logs for the port,
		// or programmatically obtain it for use in test cases.
		Server server = new Server(8081);

		// 当前版本的官网推荐写法
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/pay-shop");
		File warFile = new File("src/main/webapp");
		System.out.println(warFile.getAbsolutePath());
		webapp.setWar(warFile.getAbsolutePath());

		// 之前一些版本的写法：http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
		// WebAppContext context = new WebAppContext();
		// context.setResourceBase("src/main/webapp");
		// context.setContextPath("/shop");
		// context.setParentLoaderPriority(true);

		// A WebAppContext is a ContextHandler as well so it needs to be set to
		// the server so it is aware of where to
		// send the appropriate requests.
		server.setHandler(webapp);

		// Configure a LoginService
		// Since this example is for our test webapp, we need to setup a
		// LoginService so this shows how to create a
		// very simple hashmap based one. The name of the LoginService needs to
		// correspond to what is configured in
		// the webapp's web.xml and since it has a lifecycle of its own we
		// register it as a bean with the Jetty
		// server object so it can be started and stopped according to the
		// lifecycle of the server itself.

		// Start things up! By using the server.join() the server thread will
		// join with the current thread.
		// See
		// "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
		// for more details.
		server.start();
		server.join();

	}
}
