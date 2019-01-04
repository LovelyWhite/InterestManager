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

    private static int getConnection()
    {
        try {
            Class.forName(StaticValues.DATABASE_DRIVER);
            cn = DriverManager.getConnection(StaticValues.DATABASE_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return StaticValues.FAILED;
        }
        if(cn!=null)
            return StaticValues.OK;
        return StaticValues.FAILED;
    }
    public static int checkPassword(String userEmail,String userPassword)
    {
        int getI;
        try {
            if(cn == null||cn.isClosed())
                if(StaticValues.FAILED==getConnection())
                    return StaticValues.LINKDBFAILED;
            cs = cn.prepareCall("{call password_check(?,?,?)}");
            cs.setString(1,userEmail);
            cs.setString(2,userPassword);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.execute();
            getI = cs.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
            return StaticValues.EXCUTEFAILED;
        }
        finally {
            try {
                if(cs!=null&&!cs.isClosed())
                    cs.close();
                if(cn!=null&&!cn.isClosed())
                    cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return getI;
    }
}