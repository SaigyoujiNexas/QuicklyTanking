package com.example.modulespublic.common.base;

import androidx.room.TypeConverter;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    Gson gson = new Gson();

    @TypeConverter
    public List<LatLng> LatLngListToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<LatLng>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToLatLngList(List<LatLng> someObjects) {
        return gson.toJson(someObjects);
    }
}
