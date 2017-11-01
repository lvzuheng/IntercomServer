package sz.lzh.conversation.util.coder;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


public class JsonEncoder {
	private Map<String, Object> map = new HashMap<String, Object>();
	
	
	public JsonEncoder() {
		// TODO Auto-generated constructor stub
	}
	
	public JsonEncoder put(String key,Object value){
		map.put(key, value);
		return  this;
	}
	
	public byte[] getByteEncoder(Object header,Object body) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("header",header);
		jsonObject.put("body", body);
		return (jsonObject.toString()+"\r\n").getBytes();
	}
	public byte[] getByteEncoder(Object obj) {
		return (JSONObject.toJSON(obj).toString()+"\r\n").getBytes();
	}
	
	public String stringEncoder(){
			return encoder().toString()+"\r\n";
	}
	public JSONObject toJsonObject(){
		return encoder();
	}
	
	public byte[] byteEncoder(){
		return stringEncoder().getBytes();
	}
	
	private JSONObject encoder(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		return jsonObject;
	}
}
