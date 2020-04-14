package com.kxj.rx.bean;

/**
 * 近5天天气情况
 */
public class Future {
    private String date;
    private String temperature;
    private String weather;
    private String direct;
    private Wid wid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Wid getWid() {
        return wid;
    }

    public void setWid(Wid wid) {
        this.wid = wid;
    }

    @Override
    public String toString() {
        return "Future{" +
                "date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", direct='" + direct + '\'' +
                ", wid=" + wid +
                '}';
    }

    public class Wid {
        private String day;
        private String night;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        @Override
        public String toString() {
            return "Wid{" +
                    "day='" + day + '\'' +
                    ", night='" + night + '\'' +
                    '}';
        }
    }
}
