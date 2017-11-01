package sz.lzh.conversation.bean.receive;

import sz.lzh.conversation.bean.CodeInfo;

public class ModifyPassword extends CodeInfo{
	private String password;
	private String newPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
