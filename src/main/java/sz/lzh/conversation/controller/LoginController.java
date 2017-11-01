package sz.lzh.conversation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sz.lzh.conversation.dao.cache.infos.CacheUser;
import sz.lzh.conversation.dao.domain.impl.SqlManagerImpl;
import sz.lzh.conversation.dao.domain.infos.User;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private SqlManagerImpl sqlManagerImpl; 
	@Autowired
	private CacheUser cacheUser;
	
	public LoginController() {	
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/searchUsername")
	@ResponseBody
	public String searchUsername(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("username");
		response.setHeader("Access-Control-Allow-Origin", "*");
		return cacheUser.isExist(username)?"1":"0";
	}

	@RequestMapping("/loginApply")
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setHeader("Access-Control-Allow-Origin", "*");
		return cacheUser.isExist(username) && cacheUser.getPassWord(username).equals(password)?"1":"0";
	}
}
