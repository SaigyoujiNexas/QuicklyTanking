package com.example.modulespublic.common.net;

public class ResultBean {
    private String calorie;
    private boolean has_calorie;
    private String name;
    private BaikeInfo baike_info;

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public boolean isHas_calorie() {
        return has_calorie;
    }

    public void setHas_calorie(boolean has_calorie) {
        this.has_calorie = has_calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaikeInfo getBaike_info() {
        return baike_info;
    }

    public void setBaike_info(BaikeInfo baike_info) {
        this.baike_info = baike_info;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "calorie='" + calorie + '\'' +
                ", has_calorie=" + has_calorie +
                ", name='" + name + '\'' +
                ", baike_info=" + baike_info +
                '}';
    }
}
