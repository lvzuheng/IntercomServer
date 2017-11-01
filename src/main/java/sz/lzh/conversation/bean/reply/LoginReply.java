package sz.lzh.conversation.bean.reply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sz.lzh.conversation.bean.CodeInfo;

public class LoginReply extends CodeInfo{
	private String nickname;
	private String dateTime;
	
	public LoginReply() {
		// TODO Auto-generated constructor stub
		setDateTime();
	}
	
	public LoginReply(int pid,String username,String nickname){
		setPid(pid);
		setUsername(username);
		setNickname(nickname);
	}
	
	public String getDateTime() {
		return dateTime;
	}
	public LoginReply setDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		this.dateTime = sdf.format(new Date());
		return this;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
