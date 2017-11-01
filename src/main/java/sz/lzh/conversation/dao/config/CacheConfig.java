package sz.lzh.conversation.dao.config;

public class CacheConfig {

	/**初始化标识符*/
	public static String SYNC="sync";
	
	
	/**数据库表Key值*/
	//user数据库id
	public static String USER_ID ="USER_ID";
	//user用户密码
	public static String USER_PASSWORD ="USER_PASSWORD";
	//user最后登录时间
	public static String USER_DATATIME ="USER_DATATIME";
	//user用户名
	public static String USER_NICKNAME ="USER_NICKNAME";

	
	//organization机构Id
	public static String ORG_ORGID ="org_id";
	//organization机构名
	public static String ORG_ORGNAME ="org_orgname";
	//organization上属机构
	public static String ORG_PARENETID ="org_parentid";
	//organization机构路径
	public static String ORG_ORGPATH ="org_orgpath";
	//organization机构下的成员机构
	public static String ORG_MEMBER ="org_member";
	
	//administrator用户id
	public static String ADMIN_ID ="admin_id";
	//administrator用户密码
	public static String ADMIN_PASSWORD ="admin_password";
	//administrator用户机构
	public static String ADMIN_ORGANIZATION ="admin_organization";
	//administrator用户seq码
	public static String ADMIN_SEQNUM ="admin_seqnum";
	//administrator用户role
	public static String ADMIN_ROLE ="admin_role";
	//administrator同机构成员
	public static String ADMIN_MEMBER ="admin_member";
	
	
	//group的groupname
	public static String GROUP_ID ="GROUP_ID";
	//group的groupname
	public static String GROUP_NAME ="GROUP_NAME";
	//group的groupname
	public static String GROUP_OWNER ="GROUP_OWNER";
	
	//groupuser的id
	public static String GROUPMEMBER_ID ="GROUPMEMBER_ID";
	//groupuser的authority
	public static String GROUPMEMBER_USERNAME ="GROUPMEMBER_USERNAME";
	//groupuser的member
	public static String GROUPMEMBER_GROUPID="GROUPMEMBER_GROUPID";
	//groupuser的member
	public static String GROUPMEMBER_GRADE="GROUPMEMBER_GRADE";

}
