.bean 
	.http 				http的bean类
	.receive 			tcp协议的接收bean类
	.reply  			tcp协议的发送bean类	
.config
	Config.java         数据库与redis的配置文件保存成对象
	Configure.java      根据配置文件路径初始化配置文件，并保存信息
.dao
	.cache              缓存redis，
		.impl           redis操作，主要是对redis的增删改查操作
		.infos			redis存储的数据封装成的bean对象
		.sync			同步数据库
	.domain				Mysql数据库管理
.net
	.coder				解码器与编码器
	.connect			tcp连接
	.handler			tcp消息处理者
.service
	.action				协议类
	.inform				对协议中多次复用的的操作进行封装
.util
	.coder				解析器
		ApkUtils.java   apk解析类，负责解析apk的xml文件
	.connect
		ChannelController.java  		channel管理类，所有tcp连接建立以后，会通过这个类进行管理
	.Group								对讲组管理
		Group.java						对讲组的对象，对讲组中的成员会由对讲组对象进行管理
		GroupManager.java				对讲组的管理类，管理所有的对讲组
		Interlocutor.java				对讲组成员类，申请对讲以后，会把申请者封装成对讲者对象
		InterlocutorManager.java		对讲者管理类，管理所有的对讲者
	.manager
		CacheThreadManager.java			线程池管理类
		StreamManager.java				流转发类