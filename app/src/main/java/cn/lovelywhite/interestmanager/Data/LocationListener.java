package cn.lovelywhite.interestmanager.Data;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

public class LocationListener extends BDAbstractLocationListener {
    private double latitude;
    private double longitude;
    private float radius;
    private String coorType;
    private int errorCode;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRadius() {
        return radius;
    }

    public String getCoorType() {
        return coorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        latitude = location.getLatitude();//获取纬度信息
        longitude = location.getLongitude();//获取经度信息
        radius = location.getRadius();//获取定位精度，默认值为0.0f
        coorType = location.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        errorCode = location.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
    }
}
