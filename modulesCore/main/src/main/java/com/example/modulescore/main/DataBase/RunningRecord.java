package com.example.modulescore.main.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "RunningRecord")
public class RunningRecord {

    //主键
    //表示主键，也就是表中的主键，每个表都要至少有一个主键，
    // 当这个主键是一个字符串时，还要加上@NonNull注解，不然会出现编译错误。
    @PrimaryKey
    private Long id;

    //运动轨迹
    //表示这是数据库表中的一个列。其中的name表示此对象在表中对应的类名，
    // 如果不添加此注解，Room默认会以此变量名作为其在表中的列名。
    private List<LatLng> PathPointsLine;
    //运动距离
    private String Distance;
    //运动时长
    private Long Runningtime;
    //运动开始时间
    private Date StartTime;
    //消耗卡路里
    private String Calorie;
    //平均时速(公里/小时)
    private String Speed;
    //平均配速(分钟/公里)
    private Double Distribution;
    //日期标记
    private String DateTag;
    private String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return "RunningRecord{" +
                "id=" + id +
                ", PathPointsLine=" + PathPointsLine +
                ", Distance='" + Distance + '\'' +
                ", Runningtime=" + Runningtime +
                ", StartTime=" + StartTime +
                ", Calorie='" + Calorie + '\'' +
                ", Speed='" + Speed + '\'' +
                ", Distribution=" + Distribution +
                ", DateTag='" + DateTag + '\'' +
                '}';
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LatLng> getPathPointsLine() {
        return PathPointsLine;
    }

    public void setPathPointsLine(List<LatLng> pathPointsLine) {
        PathPointsLine = pathPointsLine;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public Long getRunningtime() {
        return Runningtime;
    }

    public void setRunningtime(Long runningtime) {
        Runningtime = runningtime;
    }

    public String getCalorie() {
        return Calorie;
    }

    public void setCalorie(String calorie) {
        Calorie = calorie;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public Double getDistribution() {
        return Distribution;
    }

    public void setDistribution(Double distribution) {
        Distribution = distribution;
    }

    public String getDateTag() {
        return DateTag;
    }

    public void setDateTag(String dateTag) {
        DateTag = dateTag;
    }
}
