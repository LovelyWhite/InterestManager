package cn.lovelywhite.interestmanager.Data;

import java.sql.Time;

public class Interest {
    private String interestId;//ID
    private double interestLongitude;//经度
    private double interestDimension;//纬度
    private double  interestPrice;//价格
    private  int interestRate; //评分
    private String interestCommentId;//评论列表
    private  String interestPic;//景区图片
    private Time openTime;//开门时间
    private Time closeTime;//关门时间
    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public double getInterestLongitude() {
        return interestLongitude;
    }

    public void setInterestLongitude(double interestLongitude) {
        this.interestLongitude = interestLongitude;
    }

    public double getInterestDimension() {
        return interestDimension;
    }

    public void setInterestDimension(double interestDimension) {
        this.interestDimension = interestDimension;
    }

    public double getInterestPrice() {
        return interestPrice;
    }

    public void setInterestPrice(double interestPrice) {
        this.interestPrice = interestPrice;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public String getInterestCommentId() {
        return interestCommentId;
    }

    public void setInterestCommentId(String interestCommentId) {
        this.interestCommentId = interestCommentId;
    }

    public String getInterestPic() {
        return interestPic;
    }

    public void setInterestPic(String interestPic) {
        this.interestPic = interestPic;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

}
