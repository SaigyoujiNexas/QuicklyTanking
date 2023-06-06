package com.xupt.modulescore.main.db;

import com.amap.api.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public class Record {
    private int id;
    private List<LatLng> pathPointsLine;
    private String distance;
    private int runningtime;
    private Date startTime;
    private String calorie;
    private String speed;
    private String distribution;
    private String username;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setPathPointsLine(List<LatLng> pathPointsLine) {
        this.pathPointsLine = pathPointsLine;
    }
    public List<LatLng> getPathPointsLine() {
        return pathPointsLine;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getDistance() {
        return distance;
    }

    public void setRunningtime(int runningtime) {
        this.runningtime = runningtime;
    }
    public int getRunningtime() {
        return runningtime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getStartTime() {
        return startTime;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
    public String getCalorie() {
        return calorie;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getSpeed() {
        return speed;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
    public String getDistribution() {
        return distribution;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

}
