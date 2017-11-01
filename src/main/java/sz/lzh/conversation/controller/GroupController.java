package sz.lzh.conversation.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.dialect.Ingres10Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.dao.config.CacheConfig;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.GroupInfo;
import sz.lzh.conversation.dao.domain.infos.User;

@Controller
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private CacheUser cacheUser;
	@Autowired
	private CacheGroupInfo cacheGroupInfo;
	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	
	@RequestMapping("/groupList")
	@ResponseBody
	public String groupList(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONArray jsonArray  = new JSONArray();
		
		for(Map.Entry<String, String> entry:cacheGroupInfo.getGroup().entrySet()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("groupId", entry.getKey());
			jsonObject.put("groupName", entry.getValue());
			List<String> uList = cacheGroupMember.getGroupUsername(Integer.valueOf(entry.getKey()));
			JSONArray uArray = new JSONArray();
			for(String user:uList){
				JSONObject uJson = new JSONObject();
				uJson.put("username", user);
				uJson.put("nickname", cacheUser.getNickName(user));
				uArray.add(uJson);
			}
			jsonObject.put("groupMember", uArray);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toJSONString();
	}
	@RequestMapping("/groupInfoList")
	@ResponseBody
	public String groupInfoList(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONArray jsonArray  = new JSONArray();
		
		for(Map.Entry<String, String> entry:cacheGroupInfo.getGroup().entrySet()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("groupId", entry.getKey());
			jsonObject.put("groupName", entry.getValue());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toJSONString();
	}
	
	
	@RequestMapping("/editGroupInfos")
	@ResponseBody
	public String editGroupInfos(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String groupId = request.getParameter("groupId");
		String groupName = request.getParameter("groupName");
		boolean status = sqlManagerImpl.updateByClassAndUpdataCache(GroupInfo.class, "name", groupName, "id", groupId, cacheGroupInfo);
		return status?"1":"0";
	}
	@RequestMapping("/deleteGroupInfos")
	@ResponseBody
	public String deleteGroupInfos(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String groupId = request.getParameter("groupId");
		String groupName = request.getParameter("groupName");
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setId(Integer.valueOf(groupId));
		groupInfo.setName(groupName);
		boolean status = sqlManagerImpl.remove("DELETE FROM GroupInfo WHERE id = "+groupId);
		cacheGroupInfo.remove(groupInfo);
		return status?"1":"0";
	}
	
	@RequestMapping("/searchGroupInfos")
	@ResponseBody
	public String searchGroupInfos(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String groupName = request.getParameter("groupName");
		return cacheGroupInfo.isExist(groupName)?"1":"0";
	}
	
	@RequestMapping("/addGroupInfos")
	@ResponseBody
	public String addGroupInfos(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String groupName = request.getParameter("groupName");
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setName(groupName);
		
//		boolean status = sqlManagerImpl.save(groupInfo);
		boolean status = sqlManagerImpl.saveAndUpdataCache(groupInfo,cacheGroupInfo);
		return status?"1":"0";
	}
	@RequestMapping("/search")
	@ResponseBody
	public String search(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String value = request.getParameter("value");
		List<Object> list = sqlManagerImpl.searchBySql("FROM GroupInfo WHERE name LIKE '%"+value+"%'");
		
		JSONArray groupArray = new JSONArray();
		
		for(int i=0;i<list.size();i++){
			JSONObject groupJson = new JSONObject();
			groupJson.put("groupId", ((GroupInfo)list.get(i)).getId());
			groupJson.put("groupName", ((GroupInfo)list.get(i)).getName());
			JSONArray memberArray = new JSONArray();
			for(String username :cacheGroupMember.getGroupUsername(((GroupInfo)list.get(i)).getId())){
				JSONObject memberJson = new JSONObject();
				memberJson.put("username", username);
				memberJson.put("nickname", cacheUser.getNickName(username));
				memberArray.add(memberJson);
			}
			groupJson.put("member", memberArray);
			groupArray.add(groupJson);
			
		}
		return null;
	}
	
	@RequestMapping(value = "/userListInfo",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String userListInfo(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> uMap = cacheUser.getUsers();
		JSONArray jsonArray = new JSONArray();
		for(Entry<String, String> entry:uMap.entrySet()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userName", entry.getKey());
			jsonObject.put("nickName", entry.getValue());
			Set<Integer> iSet = cacheGroupMember.getGroupId(entry.getKey());
			Map<String, String>gMap = cacheGroupInfo.getGroup();
			for(int i:iSet){
				gMap.remove(i);
			}
			JSONArray gArray = new JSONArray();
			for(Entry<String, String> geEntry :gMap.entrySet()){
				JSONObject gJsonObject = new JSONObject();
				gJsonObject.put("groupId", geEntry.getKey());
				gJsonObject.put("groupName", geEntry.getValue());
				gArray.add(gJsonObject);
			}
			jsonObject.put("group", gArray);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toJSONString();
	}
}
