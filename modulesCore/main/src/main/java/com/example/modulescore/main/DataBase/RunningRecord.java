package com.example.modulescore.main.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "RunningRecord")
public class RunningRecord {

    //主键
    //表示主键，也就是表中的主键，每个表都要至少有一个主键，
    // 当这个主键是一个字符串时，还要加上@NonNull注解，不然会出现编译错误。
    @PrimaryKey
    private Long id;
    @Ignore
    //运动开始点
    private LatLng mStartPoint;
    @Ignore
    //运动结束点
    private LatLng mEndPoint;

    //运动轨迹
    //表示这是数据库表中的一个列。其中的name表示此对象在表中对应的类名，
    // 如果不添加此注解，Room默认会以此变量名作为其在表中的列名。
    @TypeConverters(LatLngTypeConverter.class)
    private List<LatLng> mPathPointsLine;
    //运动距离
    private String mDistance;
    //运动时长
    private Long mRunningtime;
    //运动开始时间
    @Ignore
    private Long mStartTime;
    //运动结束时间
    @Ignore
    private Long mEndTime;
    //消耗卡路里
    @Ignore
    private Double mCalorie;
    //平均时速(公里/小时)
    @Ignore
    private String mSpeed;
    //平均配速(分钟/公里)
    @Ignore
    private Double mDistribution;
    //日期标记
    @Ignore
    private String mDateTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LatLng getMStartPoint() {
        return mStartPoint;
    }

    public void setMStartPoint(LatLng mStartPoint) {
        this.mStartPoint = mStartPoint;
    }

    public LatLng getMEndPoint() {
        return mEndPoint;
    }

    public void setMEndPoint(LatLng mEndPoint) {
        this.mEndPoint = mEndPoint;
    }

    public List<LatLng> getMPathPointsLine() {
        return mPathPointsLine;
    }

    public void setMPathPointsLine(List<LatLng> mPathPointsLine) {
        this.mPathPointsLine = mPathPointsLine;
    }

    public String getMDistance() {
        return mDistance;
    }

    public void setMDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    public Long getMRunningtime() {
        return mRunningtime;
    }

    public void setMRunningtime(Long mRunningtime) {
        this.mRunningtime = mRunningtime;
    }

    public Long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(Long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public Long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(Long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public Double getmCalorie() {
        return mCalorie;
    }

    public void setmCalorie(Double mCalorie) {
        this.mCalorie = mCalorie;
    }

    public String getmSpeed() {
        return mSpeed;
    }

    public void setMSpeed(String mSpeed) {
        this.mSpeed = mSpeed;
    }

    public Double getmDistribution() {
        return mDistribution;
    }

    public void setmDistribution(Double mDistribution) {
        this.mDistribution = mDistribution;
    }

    public String getmDateTag() {
        return mDateTag;
    }

    public void setmDateTag(String mDateTag) {
        this.mDateTag = mDateTag;
    }


    public RunningRecord() {
    }

    @Override
    public String toString() {
        return "RunningRecord{" +
                "id=" + id +
                ", mPathPointsLine=" + mPathPointsLine +
                ", mDistance='" + mDistance + '\'' +
                ", mCalorie=" + mCalorie +
                ", mSpeed='" + mSpeed + '\'' +
                '}';
    }
}
