package cn.lovelywhite.interestmanager.Data;

import android.app.Application;
import android.content.Context;


//定义数据常量
public class StaticValues{

    public final static int OK = 0;
    public final static int FAILED = -1;
    public final static int LINKDBFAILED = -2;
    public final static int EXCUTEFAILED = -3;
    public final static int USEREPASSWPRDEMPTY = -4;
    public final static int USEREMAILEMPTY = -5;
    //定义用户类型
    public final static int ADMIN = 1;//管理员
    public final static int ORDINARY = 0;//普通

    //定义数据库链接
    public final static String DATABASE_URL = "jdbc:mysql://39.105.171.169:3306/interest_manage?user=root&password=Mishiweilai123&useSSL=false&TimeZone=UTC";
    public final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    public static Context AppContext;

}
