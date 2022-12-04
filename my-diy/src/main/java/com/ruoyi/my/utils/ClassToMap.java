package com.ruoyi.my.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassToMap {

    public static Map<String, Object> getMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<? extends Object> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null) {
                    map.put(f.getName(), f.get(obj));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
