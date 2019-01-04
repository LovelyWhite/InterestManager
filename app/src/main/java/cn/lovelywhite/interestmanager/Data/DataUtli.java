package cn.lovelywhite.interestmanager.Data;
import android.content.res.Resources;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import cn.lovelywhite.interestmanager.R;

public class DataUtli {
    private static Connection cn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private static CallableStatement cs = null;

    private static int getConnection() {
        try {
            Class.forName(StaticValues.DATABASE_DRIVER);
            cn = DriverManager.getConnection(StaticValues.DATABASE_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return StaticValues.FAILED;
        }
        if (cn != null)
            return StaticValues.OK;
        return StaticValues.FAILED;
    }

    public static int checkPassword(String userEmail, String userPassword) {
        if (userEmail == null || userEmail.isEmpty()) {
            return StaticValues.USEREMAILEMPTY;
        }
        if (userPassword == null || userPassword.isEmpty()) {
            return StaticValues.USEREPASSWPRDEMPTY;
        } else {
            int getI;
            try {
                if (cn == null || cn.isClosed())
                    if (StaticValues.FAILED == getConnection())
                        return StaticValues.LINKDBFAILED;
                cs = cn.prepareCall("{call password_check(?,?,?)}");
                cs.setString(1, userEmail);
                cs.setString(2, userPassword);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                getI = cs.getInt(3);
            } catch (SQLException e) {
                e.printStackTrace();
                return StaticValues.EXCUTEFAILED;
            } finally {
                try {
                    if (cs != null && !cs.isClosed())
                        cs.close();
                    if (cn != null && !cn.isClosed())
                        cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return getI;
        }
    }

    public static int sign(String userEmail, String userPassword) {
        if (userEmail == null || userEmail.isEmpty()) {
            return StaticValues.USEREMAILEMPTY;
        } else {
            int getI;
            try {
                if (cn == null || cn.isClosed())
                    if (StaticValues.FAILED == getConnection())
                        return StaticValues.LINKDBFAILED;
                cs = cn.prepareCall("{call user_sign(?,?,?)}");
                cs.setString(1, userEmail);
                cs.setString(2, userPassword);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                getI = cs.getInt(3);
            } catch (SQLException e) {
                e.printStackTrace();
                return StaticValues.EXCUTEFAILED;
            } finally {
                try {
                    if (cs != null && !cs.isClosed())
                        cs.close();
                    if (cn != null && !cn.isClosed())
                        cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return getI;
        }
    }

    public static User getUser(String userEmail)//用于返回已登录的数据(可以保证有数据)
    {
        User user = new User();
        try {
            if (cn == null || cn.isClosed())
                if (StaticValues.FAILED == getConnection())
                    return user;
            cs = cn.prepareCall("{call get_user(?)}");
            cs.setString(1, userEmail);
            cs.execute();
            rs = cs.getResultSet();
            while (rs.next())
            {
                user.setUserEmail(rs.getString(1));
                user.setUserName(rs.getString(2));
                user.setUserPassword(rs.getString(3));
                user.setUserCreateTime(rs.getTimestamp(4));
                user.setUserHeadImage(rs.getString(5));
                user.setUserType(rs.getInt(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        } finally {
            try {
                if (cs != null && !cs.isClosed())
                    cs.close();
                if (cn != null && !cn.isClosed())
                    cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}