package com.byanda.test.userTest.testClient;

import com.alibaba.fastjson.JSON;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.receive.Login;

public class WriteMessageToClient {

	public void write(Channel channel){
		
		Login login = new Login();
		login.setPassword("e10adc3949ba59abbe56e057f20f883e");
		login.setPid(6001);
		login.setUsername("zdm");
		channel.writeAndFlush((JSON.toJSONString(login)+"\r\n").getBytes());

	}
}
