package sz.lzh.conversation.bean.receive;

import sz.lzh.conversation.bean.CodeInfo;

public class IntercomRequest extends CodeInfo{

	private boolean action;

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

}
