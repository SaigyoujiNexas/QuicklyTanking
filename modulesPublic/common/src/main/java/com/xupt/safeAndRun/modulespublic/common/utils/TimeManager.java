package com.xupt.safeAndRun.modulespublic.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {
    //开始时间
    String settedTime;


    static SimpleDateFormat yearFormat = new SimpleDateFormat ("yyyy年");
    static SimpleDateFormat dateFormat = new SimpleDateFormat ("MM月dd日");
    static SimpleDateFormat minuteFormat = new SimpleDateFormat ("hh:mm");
//    public static String calculatePassedTime(Date startTime){
//
//        //获得持续秒数
//        passedSeconds = (int) (new Date().getTime() - startTime.getTime()) / 1000;
//        String formattedPassedTime = getFormattedTime(passedSeconds);//将持续秒数转化为mm:s
//        return formattedPassedTime;
//    }
    /**
     * 将持续时间转换为时分秒
     * @param passedSeconds total seconds
     * @return HH:mm:ss
     */
    public static String getFormattedTime(int passedSeconds){
        int hours = 0, minutes = 0, seconds = 0;
        StringBuilder sb = new StringBuilder();

        //小时 = passedTime/3600秒
//        hours = passedTime / 3600;
//        passedSeconds = passedSeconds % 3600;
//        if (hours < 10){
//            sb.append("0");
//        }
//        sb.append(hours + ":");

        //分钟 = passedSeconds/60秒
        minutes = passedSeconds / 60;
        passedSeconds = passedSeconds % 60;
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes + ":");

        //分钟 = passedSeconds
        seconds = passedSeconds;
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds + "");

        return sb.toString();
    }

    public static String formatseconds(Long passedSeconds) {
        String mm = (passedSeconds % 3600) / 60 > 9 ? String.valueOf((passedSeconds % 3600) / 60)
                : "0" + (passedSeconds % 3600) / 60;
        String ss = (passedSeconds % 3600) % 60 > 9 ? String.valueOf((passedSeconds % 3600) % 60)
                : "0" + (passedSeconds % 3600) % 60;

        return  mm + ":" + ss;
    }
    public static String formatYears(Date date){
        return yearFormat.format(date);
    }
    public static String formatMinutes(Date date){
        return minuteFormat.format(date);
    }
    public static String formatDates(Date date){
        return dateFormat.format(date);
    }
}
