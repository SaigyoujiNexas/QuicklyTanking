package com.example.modulespublic.common.base;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "RunningRecord")
public class RunningRecord  {
    @PrimaryKey
    @NonNull
    //主键
    //表示主键，也就是表中的主键，每个表都要至少有一个主键，
    // 当这个主键是一个字符串时，还要加上@NonNull注解，不然会出现编译错误。
    private int id;
    //运动轨迹
    //表示这是数据库表中的一个列。其中的name表示此对象在表中对应的类名，
    // 如果不添加此注解，Room默认会以此变量名作为其在表中的列名。
    private List<LatLng> pathPointsLine;
    //运动距离
    private double distance;
    //运动时长
    private Long runningtime;
    //运动开始时间
    private String startTime;
    //消耗卡路里
    private String calorie;
    //平均时速(公里/小时)
    private String speed;
    //平均配速(分钟/公里)
    private Double distribution;

    private String username;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LatLng> getPathPointsLine() {
        return pathPointsLine;
    }

    public void setPathPointsLine(List<LatLng> pathPointsLine) {
        this.pathPointsLine = pathPointsLine;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getRunningtime() {
        return runningtime;
    }

    public void setRunningtime(Long runningtime) {
        this.runningtime = runningtime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Double getDistribution() {
        return distribution;
    }

    public void setDistribution(Double distribution) {
        this.distribution = distribution;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
