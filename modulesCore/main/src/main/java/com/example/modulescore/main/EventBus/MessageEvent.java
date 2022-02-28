package com.example.modulescore.main.EventBus;


public class MessageEvent {
    private String formattedPassedTime;
    private String distance;
    private String speed;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getFormattedPassedTime() {
        return formattedPassedTime;
    }

    public void setFormattedPassedTime(String formattedPassedTime) {
        this.formattedPassedTime = formattedPassedTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
