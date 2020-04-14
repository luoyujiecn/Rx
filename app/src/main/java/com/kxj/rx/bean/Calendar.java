package com.kxj.rx.bean;

/**
 * @author luo
 */
public class Calendar {
    private String holiday;//假日
    private String avoid;//忌
    private String animalsYear;//属相
    private String desc;//假日描述
    private String weekday;//周几
    private String suit;//破屋.坏垣.求医.治病.
    private String lunarYear;//纪年
    private String lunar;//农历
    private String date;//具体日期

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "holiday='" + holiday + '\'' +
                ", avoid='" + avoid + '\'' +
                ", animalsYear='" + animalsYear + '\'' +
                ", desc='" + desc + '\'' +
                ", weekday='" + weekday + '\'' +
                ", suit='" + suit + '\'' +
                ", lunarYear='" + lunarYear + '\'' +
                ", lunar='" + lunar + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
