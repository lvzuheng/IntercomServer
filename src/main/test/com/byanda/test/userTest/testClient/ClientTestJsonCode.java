package com.byanda.test.userTest.testClient;

public class ClientTestJsonCode {
	public static String login = "{\"header\":{\"uid\":\"000003\",\"pid\":6001},\"body\":{\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"status\":\"C\"}}\r\n";
	public static String userList = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6009},\"body\":{\"orgid\":\"1\",\"type\":\"1\"}}\r\n";
	public static String clientList = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6008},\"body\":{\"orgid\":\"1\"}}\r\n";
	public static String location = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6030},\"body\":{\"gpsStatus\":\"A\",\"date\":\"170128 111111\",\"longitude\":\"2266\",\"latitude\":\"6622\",\"speed\":\"10\",\"direction\":\"N\",\"totalStorage\":\"\",\"valueStorage\":\"\",\"electricity\":\"\"}}\r\n";
	public static String callrequest = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6069},\"body\":{\"groupid\":1}}\r\n";
	public static String callrequestiNFO = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6070},\"body\":{\"oper\":1}}\r\n";
	public static String Stream =  "{\"header\":{\"version\":\"v1\",\"uid\":\"000001\",\"pid\":6077},\"body\":{\"team\":1,\"authority\":1,\"clients\":[],\"users\":[{\"user\":\"000001\"}],\"stream\":[1,2,4,1,2,3]}}\r\n";
	public static String Heart = "{\"header\":{\"version\":\"v1\",\"pid\":6004,\"uid\":\"LZC\"},\"body\":{}}\r\n";
	public static String callReply = "{\"header\":{\"version\":\"v1\",\"uid\":\"LZC\",\"pid\":6033},\"body\":{\"action\": [1,0,0,1],\"user\":\"000001\"}}\r\n";
	public static String StreamRequest = "{\"header\":{\"version\":\"v1\",\"pid\":6022,\"uid\":\"LZC\"},\"body\":{\"action\": [1,0,0,1],\"eventMessage\":\"处理业务\",\"user\":\"000001\"}}\r\n";
}
