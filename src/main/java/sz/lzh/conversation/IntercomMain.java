package sz.lzh.conversation;

import org.springframework.context.ApplicationContext;

import sz.lzh.conversation.net.connect.MainServer;

public class IntercomMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args==null || args.length ==0){
			MainServer.bootUp();
			return;
		}
	    if("start".equals(args[0])){
	        //启动
	    	System.out.println("starting process.........");
	    	MainServer.bootUp();
	    	return;
	    }
	    else if("stop".equals(args[0])){
	        //停止
	    	System.out.println("stopping process.........");
	        System.exit(0);
	        return;
	    }
	    MainServer.bootUp();
	}

}
