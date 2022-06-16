package com.xupt.safeAndRun.modulesbase.libbase.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

    public static Class<?> analysisClassInfo(Object obj){
        Type genType = obj.getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) genType;

        Type[] params = pType.getActualTypeArguments();
        Type type0 = params[0];
        return (Class<?>) type0;
    }
}
