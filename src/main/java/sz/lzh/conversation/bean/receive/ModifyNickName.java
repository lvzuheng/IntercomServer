package sz.lzh.conversation.bean.receive;

import sz.lzh.conversation.bean.CodeInfo;

public class ModifyNickName  extends CodeInfo{
	private String password;
	private String newNickName;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewNickName() {
		return newNickName;
	}
	public void setNewNickName(String newNickName) {
		this.newNickName = newNickName;
	}
}
