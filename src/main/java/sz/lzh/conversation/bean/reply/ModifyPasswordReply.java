package sz.lzh.conversation.bean.reply;

import sz.lzh.conversation.bean.CodeInfo;

public class ModifyPasswordReply extends CodeInfo{
		
		public ModifyPasswordReply(int pid,String username,boolean status , int reason) {
			// TODO Auto-generated constructor stub
			setPid(pid);
			setUsername(username);
			setReason(reason);
			setStatus(status);
		}
		public ModifyPasswordReply() {
			// TODO Auto-generated constructor stub
		}
		
}
