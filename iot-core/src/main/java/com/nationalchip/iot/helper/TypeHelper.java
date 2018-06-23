package com.nationalchip.iot.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 9:25 AM
 * @Modified:
 */
public class TypeHelper {
    public static  <V,E> Optional<V> tryCast(E entity){
        V v=null;
        try {
            v = (V) entity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(v);
    }

    public static Type[] getGenericTypes(Class<?> clazz){
        if(clazz == null)
            return null;

        Type type = clazz.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            return ((ParameterizedType)type).getActualTypeArguments();
        }

        return null;
    }


}
