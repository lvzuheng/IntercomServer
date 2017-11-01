package com.byanda.test.userTest.testTerminal;

public class TerminalTestJsonCode {
	public static String login = "{\"username\":\"000001\",\"pid\":6001,\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}\r\n";
	public static String userList = "{\"header\":{\"uid\":\"000001\",\"pid\":6006},\"body\":{\"type\":\"1\"}}\r\n";
	public static String clientList = "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7008},\"body\":{\"orgid\":\"1\"}}\r\n";
	public static String location = "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7030},\"body\":{\"gpsStatus\":\"A\",\"date\":\"170128 111111\",\"longitude\":\"2266\",\"latitude\":\"6622\",\"speed\":\"10\",\"direction\":\"N\",\"totalStorage\":\"\",\"valueStorage\":\"\",\"electricity\":\"\"}}\r\n";
	public static String callrequest = "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7069},\"body\":{\"groupid\":1}}\r\n";
	public static String callrequestiNFO = "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7070},\"body\":{\"oper\":1}}\r\n";
	public static String Stream =  "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7077},\"body\":{\"team\":2,\"authority\":1,\"stream\":[1,2,4,1,2,3]}}\r\n";
	public static String VideoStream =  "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7099},\"body\":{\"team\":2,\"authority\":1,\"stream\":[3,4,45,321,242,3111]}}\r\n";
	public static String Heart = "{\"header\":{\"version\":\"v1\",\"pid\":8000,\"uid\":\"000001\"},\"body\":{}}\r\n";
	public static String callReply = "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":7033},\"body\":{\"action\": [1,0,1,1],\"user\":\"LZC\"}}\r\n";
	public static String StreamRequest = "{\"header\":{\"version\":\"v1\",\"pid\":7022,\"uid\":\"000001\"},\"body\":{\"action\": [0,0,1,1],\"eventMessage\":\"处理业务\",\"user\":\"LZC\"}}\r\n";
	public static String teamrequest = "{\"header\":{\"version\":\"v1\",\"pid\":7010,\"uid\":\"000001\"},\"body\":{}}\r\n";
	public static String ModifyUserName = "{\"header\":{\"pid\":6004,\"uid\":\"000001\"},\"body\":{\"newUserName\":\"aac\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",}}\r\n";
	public static String ModifyPassWord = "{\"header\":{\"pid\":6005,\"uid\":\"000001\"},\"body\":{\"newPassWord\":\"cacacacasdasdasfewfaewrfqw\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",}}\r\n";
	
}
