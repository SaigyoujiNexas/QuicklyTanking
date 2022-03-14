package com.example.modulescore.main.EventBus;


import com.amap.api.maps.model.LatLng;
import com.example.modulescore.main.DataBase.RunningRecord;

import java.util.Date;
import java.util.List;

public class MessageEvent {
    private Long formattedPassedTime;
    private String distance;
    private String speed;
    private RunningRecord runningRecord;
    private String calorie;
    private  List<LatLng> mPathPointsLine;
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<LatLng> getmPathPointsLine() {
        return mPathPointsLine;
    }

    public void setmPathPointsLine(List<LatLng> mPathPointsLine) {
        this.mPathPointsLine = mPathPointsLine;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public RunningRecord getRunningRecord() {
        return runningRecord;
    }

    public void setRunningRecord(RunningRecord runningRecord) {
        this.runningRecord = runningRecord;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Long getFormattedPassedTime() {
        return formattedPassedTime;
    }

    public void setFormattedPassedTime(Long formattedPassedTime) {
        this.formattedPassedTime = formattedPassedTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
