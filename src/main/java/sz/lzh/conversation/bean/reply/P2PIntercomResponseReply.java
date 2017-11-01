package sz.lzh.conversation.bean.reply;

import sz.lzh.conversation.bean.CodeInfo;

public class P2PIntercomResponseReply extends CodeInfo{

	private boolean answer;
	private String receiver;
	
	public P2PIntercomResponseReply(int pid,String username,String receiver,boolean answer) {
		// TODO Auto-generated constructor stub
		setPid(pid);
		setUsername(username);
		setReceiver(receiver);
		setAnswer(answer);
	}
	
	public P2PIntercomResponseReply() {
		// TODO Auto-generated constructor stub
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
}
