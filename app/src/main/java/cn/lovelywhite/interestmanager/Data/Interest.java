package cn.lovelywhite.interestmanager.Data;

import java.sql.Time;

public class Interest {
    private String interestId;//ID
    private String interestName;//景区名称
    private double interestLongitude;//经度
    private double interestDimension;//纬度
    private float ticketPrice;//价格
    private float interestRate; //评分
    private String interestDescription;//描述
    private  String interestPic;//景区图片
    private Time openTime;//开门时间
    private Time closeTime;//关门时间
    private int ticketRemaining;//剩余票量

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
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

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getInterestDescription() {
        return interestDescription;
    }

    public void setInterestDescription(String interestDescription) {
        this.interestDescription = interestDescription;
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

    public int getTicketRemaining() {
        return ticketRemaining;
    }

    public void setTicketRemaining(int ticketRemaining) {
        this.ticketRemaining = ticketRemaining;
    }
}
