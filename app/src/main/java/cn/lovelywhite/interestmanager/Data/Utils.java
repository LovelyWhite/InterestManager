package cn.lovelywhite.interestmanager.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import static cn.lovelywhite.interestmanager.Data.StaticValues.ADMIN;

public class Utils {
    public static Bitmap stringToBitmap(String string) {
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static String getType (int type)
    {
        switch (type)
        {
            case  StaticValues.ADMIN: return "超级管理员";
            case StaticValues.ORDINARY:return "普通管理员";
        }
        return "";
    }
}
