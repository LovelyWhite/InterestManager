package cn.lovelywhite.interestmanager.Data;

//定义数据常量
public class StaticValues {

    public final static int OK = 0;
    public final static int FAILED = -1;
    public final static int LINKDBFAILED = -2;
    public final static int EXCUTEFAILED = -3;
    public final static int EMAILNOTFOUND = -4;
    //定义用户类型
    public final static int ADMIN = 1;//管理员
    public final static int GUEST = 0;//游客
    public final static int ORDINARY = -1;//普通

    //定义数据库链接
    public final static String DATABASE_URL = "jdbc:mysql://39.105.171.169:3306/interest_manage?user=root&password=Mishiweilai123&useSSL=false&TimeZone=UTC";
    public final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
}
