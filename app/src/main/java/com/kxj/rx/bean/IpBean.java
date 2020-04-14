package com.kxj.rx.bean;

public class IpBean {
    private String Country;
    private String Province;
    private String City;
    private String Isp;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getIsp() {
        return Isp;
    }

    public void setIsp(String isp) {
        Isp = isp;
    }

    @Override
    public String toString() {
        return "IpBean{" +
                "Country='" + Country + '\'' +
                ", Province='" + Province + '\'' +
                ", City='" + City + '\'' +
                ", Isp='" + Isp + '\'' +
                '}';
    }
}
