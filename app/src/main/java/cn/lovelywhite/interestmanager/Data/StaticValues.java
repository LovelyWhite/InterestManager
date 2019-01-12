package cn.lovelywhite.interestmanager.Data;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


//定义数据常量
public class StaticValues{

    public final static int OK = -9;
    public final static int FAILED = -1;
    public final static int LINKDBFAILED = -2;
    public final static int EXCUTEFAILED = -3;
    public final static int USEREMAILEMPTY = -5;
    public final static int DELETEOK = -6;
    public final static int DELETEFAILED = -7;
    public final static int NOTHISUSER = -8;
    //定义用户类型
    public final static int TOURIST = -1;//游客  在本软件用不到
    public final static int ADMIN = 1;//超级管理员
    public final static int ORDINARY = 0;//普通管理员
    public static Map<String,Fragment> fG  = new HashMap<>();
    //定义数据库链接
    public final static String DATABASE_URL = "jdbc:mysql://39.105.171.169:3306/interest_manage?user=root&password=Mishiweilai123&useSSL=false&TimeZone=UTC";
    public final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";


    //定义登陆的用户数据
    public static User user = new User();


    //定义景点列表数据组
    public static Vector<Interest> InterestsData = new Vector<>();


    //定义用户列表数据组
    public static Vector<User> UsersData= new Vector<>();

    //定义评论列表数据组
    public static Vector<Comment> CommentsData = new Vector<>();
    //景点细节位置
    public static int InterestPosition;

    public static int UserPosition;

    public static Interest interest;
}
