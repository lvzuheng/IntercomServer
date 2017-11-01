package com.byanda.test.userTest.testTerminal;

import com.lzh.net.connect.TcpConnector;

public class TerminalMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TcpConnector connector = new TcpConnector();
//		for(int i =0;i<100;i++)
		connector.create().setAddress("127.0.0.1").setAUTO(false).setPort(6666).setHandler(new TerminalTestHandler().setTIMEOUT(10)).connect();
	}

}
