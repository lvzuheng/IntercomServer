package sz.lzh.conversation.util.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.mysql.jdbc.log.Log;

import sz.lzh.conversation.bean.reply.StreamReply;
import sz.lzh.conversation.config.ActionConfig;
import sz.lzh.conversation.service.inform.GroupInforms;
import sz.lzh.conversation.service.inform.IntercomInforms;
import sz.lzh.conversation.util.Group.Group;
import sz.lzh.conversation.util.Group.Interlocutor;
import sz.lzh.conversation.util.coder.JsonEncoder;

/**
 * 
 * 流线程管理器
 * @author lzh
 * @data 2017/8/30
 * 用于管理流的传输以及状态监控
 * 
 * 
 * */
public class StreamManager extends Thread {

	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	private Map<String, Queue<byte[]>> qMap = new HashMap<String, Queue<byte[]>>();
	private static StreamManager streamManager;

	public static StreamManager getInstance(){
		if( streamManager == null){
			streamManager = new StreamManager();
		}
		return streamManager;
	}



	public void send(final Interlocutor interlocutor,final byte[] stream){
		qMap.get(interlocutor.getUsername()).offer(stream);
	}



	
	/**
	 * 
	 * 组内对讲线程
	 * 
	 * 
	 * 申请一个队列存储流数据
	 * 申请一个线程转发数据同时监控栈的信息
	 * 角色的对讲标识符停止，结束线程
	 * 角色的对讲标识符未停止且栈为空，停止标识符
	 * 
	 * */
	public void setIntercom(final Interlocutor interlocutor){
		if(qMap.containsKey(interlocutor.getUsername()))
			return;

		final Queue<byte[]> queue = new LinkedList<byte[]>();
		qMap.put(interlocutor.getUsername(),queue);
		System.err.println(interlocutor.getUsername()+"申请对讲并注册");
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				Group group = interlocutor.getGroup();
				while(interlocutor!=null){
					System.err.println("开始给"+interlocutor.getUsername()+"发送语音对讲信息");
					while(!queue.isEmpty()){
						List<Interlocutor> list = group.getOtherMemberList(interlocutor.getUsername());
						IntercomInforms.StreamInform(list, interlocutor.getUsername(), queue.poll());
					}
					if(!group.getStatus()){//如果队列中不存在可以转发的数据流，则判断组的对讲状态，如果对讲状态关闭，则移除对讲信息，并退出循环
						System.err.println("给"+interlocutor.getUsername()+"发送关闭对讲信息");
						IntercomInforms.closeInform(group.getOtherMemberList(interlocutor.getUsername()), interlocutor.getUsername());
						qMap.remove(interlocutor.getUsername());
						return;
					}
					try {
						sleep(500);
						if(group.getStatus() && queue.isEmpty())//延迟500MS以后没有继续发送流，且对讲状态没有关闭，判断为异常关闭对讲
							group.closeIntercom();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 
	 * 点对点对讲线程
	 * 
	 * 申请一个队列存储流数据
	 * 申请一个线程转发数据
	 * 允许栈为空
	 * 只有当角色的点对点标识符停止才停止线程
	 * 
	 * */
	public void setP2PIntercom(final Interlocutor interlocutor){
		if(qMap.containsKey(interlocutor.getUsername()))
			return;
		//如果出现线程异常错误，替换为LinkedBlockingQueue;
		final Queue<byte[]> queue = new LinkedList<byte[]>();
		qMap.put(interlocutor.getUsername(),queue);
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				while( interlocutor.getP2PStatus()){
//					System.out.println(interlocutor.getUsername()+",interlocutor.isP2PStatus():"+interlocutor.getP2PStatus());
					while(!queue.isEmpty()){
						System.err.println(interlocutor.getUsername()+"正在点对点说话");
						IntercomInforms.P2PStreamInform(interlocutor, queue.poll());
					}
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				qMap.remove(interlocutor.getUsername());
			}

		});
	}

}
