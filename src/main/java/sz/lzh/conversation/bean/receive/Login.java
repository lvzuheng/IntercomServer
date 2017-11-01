package sz.lzh.conversation.bean.receive;

import sz.lzh.conversation.bean.CodeInfo;

public class Login extends CodeInfo{
	
	private String password;
	private String status;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
