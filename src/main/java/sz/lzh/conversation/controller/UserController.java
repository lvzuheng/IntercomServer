package sz.lzh.conversation.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sz.lzh.conversation.dao.cache.infos.CacheGroupInfo;
import sz.lzh.conversation.dao.cache.infos.CacheGroupMember;
import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.GroupMember;
import sz.lzh.conversation.dao.domain.infos.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CacheUser cacheUser;
	@Autowired
	private CacheGroupMember cacheGroupMember;
	@Autowired
	private CacheGroupInfo cacheGroupInfo;
	@Autowired
	private SqlManagerImpl sqlManagerImpl;
	
	@RequestMapping("/userList")
	@ResponseBody
	public String userList(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, String> uMap = cacheUser.getUsers();
		JSONArray jsonArray = new JSONArray();
		for(Entry<String, String> entry :uMap.entrySet()){
			JSONObject uJsonObject = new JSONObject();
			uJsonObject.put("username", entry.getKey());
			uJsonObject.put("nickname", entry.getValue());
			jsonArray.add(uJsonObject);
		}
		return jsonArray.toJSONString();
	}
	
	@RequestMapping("/editUser")
	@ResponseBody
	public String editUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String username = request.getParameter("userName");
		String nickname = request.getParameter("nickName");
		boolean status = sqlManagerImpl.updateByClassAndUpdataCache(User.class, "nickname", nickname, "username", username, cacheUser);
		return status?"1":"0";
	}
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userName = request.getParameter("userName");
		cacheUser.removeByUsername(userName);
		cacheGroupMember.removeByUsername(userName);
		boolean status = sqlManagerImpl.remove("DELETE FROM User WHERE username = '"+userName+"'");
		return status?"1":"0";
	}
	
	@RequestMapping("/searchUser")
	@ResponseBody
	public String searchUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String username = request.getParameter("userName");
		return cacheUser.isExist(username)?"1":"0";
	}
	
	@RequestMapping("/searchGroup")
	@ResponseBody
	public String searchGroup(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userName = request.getParameter("userName");
		Set<Integer> gIdSet = cacheGroupMember.getGroupId(userName);
		Map<String,String> gMap = cacheGroupInfo.getGroup();
		JSONArray jsonArray = new JSONArray();
		for(int i :gIdSet){
			gMap.remove(i);
		}
		for(Entry<String, String> entry :gMap.entrySet()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("groupId", entry.getKey());
			jsonObject.put("groupName", entry.getValue());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toJSONString();
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public String addUser(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		String username = request.getParameter("userName");
		String nickname = request.getParameter("nickName");
		String password = request.getParameter("passWord");
		String groupId = request.getParameter("groupId");
		User user = new User();
		user.setUsername(username);
		user.setNickname(nickname);
		user.setPassword(password);
		GroupMember groupMember = new GroupMember();
		groupMember.setUsername(username);
		groupMember.setGroupId(Integer.valueOf(groupId));
		boolean status1 = sqlManagerImpl.saveAndUpdataCache(user, cacheUser);
		if(status1){
			return sqlManagerImpl.saveAndUpdataCache(groupMember, cacheGroupMember)?"1":"0";
		}
		return "0";
	}
	
	@RequestMapping("/userListInfo")
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
		System.out.println(jsonArray.toJSONString());
		return jsonArray.toJSONString();
	}
	
}
