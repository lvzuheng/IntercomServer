package com.byanda.test.userTest.testTerminal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import sz.lzh.conversation.bean.receive.Login;

public class WriteMessage {
	public void write(Channel channel){
//		byte[] bs= "{\"group\":1,\"users\":[{\"user\":\"ac258\"},{\"user\":\"g201\"},{\"user\":\"g203\"}],\"authority\":1}\r\n".getBytes();
		Login login = new Login();
		login.setPassword("e10adc3949ba59abbe56e057f20f883e");
		login.setPid(6001);
		login.setUsername("ly");
		channel.writeAndFlush((JSON.toJSONString(login)+"\r\n").getBytes());
	}
}
