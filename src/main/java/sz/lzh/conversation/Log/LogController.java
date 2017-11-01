package sz.lzh.conversation.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import sz.lzh.LogController.Action.InfosLogin;
import sz.lzh.LogController.Action.InfosLoginAction;
import sz.lzh.LogController.bean.LogLogin;
import sz.lzh.LogController.net.LogServer;
import sz.lzh.conversation.dao.cache.infos.CacheUser;

@Service
public class LogController {
	
	@Autowired
	private CacheUser cacheUser;
	
	public LogController() {
		// TODO Auto-generated constructor stub
	}
	
	public void openLogController(){
		InfosLoginAction.setInfosLogin(new InfosLogin() {
			
			public boolean excute(String msg) {
				// TODO Auto-generated method stub
				LogLogin logLogin = JSONObject.parseObject(msg,LogLogin.class);
				if(cacheUser.isExist(logLogin.getUsername()) && cacheUser.getPassWord(logLogin.getUsername()).equals(logLogin.getPassword()))
					return true;
				return false;
			}
		});
	
		LogServer logServer = new LogServer();
		logServer.bootUp();
	}
}
