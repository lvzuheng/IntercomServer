package com.byanda.test.userTest.testClient;

import com.lzh.net.connect.TcpConnector;

public class ClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TcpConnector connector = new TcpConnector();
		connector.create().setAddress("10.20.175.147").setPort(6666).setHandler(new ClientTestHandler()).setAUTO(false).connect();
	}

}
