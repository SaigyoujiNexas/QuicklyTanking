package com.example.modulespublic.common.base;

import androidx.room.TypeConverter;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class LatLngTypeConverter {

    Gson gson = new Gson();

    @TypeConverter
    public List<LatLng> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<LatLng>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<LatLng> someObjects) {
        return gson.toJson(someObjects);
    }
}
