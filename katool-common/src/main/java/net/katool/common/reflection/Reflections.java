package net.katool.common.reflection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.katool.common.annotation.FieldMapKey;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hongchen.cao
 * @since 22 四月 2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reflections {
    public static Object getFieldValue(String fieldName, Object target) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        assert field != null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return ReflectionUtils.getField(field, target);
    }

    public static Object getFieldValue(Field field, Object target) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return ReflectionUtils.getField(field, target);
    }

    public static void setFieldValue(Field field, Object value, Object target) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        ReflectionUtils.setField(field, target, value);
    }

    public static Map<String, String> toStringMapIfFieldNotEmpty(Object object) {
        Map<String, Object> map = toMapIfFieldNotEmpty(object);
        Map<String, String> retMap = Maps.newHashMap();
        map.forEach((k, v) -> retMap.put(k, v.toString()));
        return retMap;
    }

    public static Map<String, Object> toMapIfFieldNotEmpty(Object object) {
        Map<String, Object> retMap = Maps.newHashMap();
        List<Field> allFields = getAllFields(object.getClass());
        for (Field field : allFields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            FieldMapKey fieldMapKey = field.getAnnotation(FieldMapKey.class);
            if (Objects.nonNull(fieldMapKey) && fieldMapKey.ignore()) {
                continue;
            }
            String key = Objects.nonNull(fieldMapKey) ? fieldMapKey.value() : field.getName();
            Object val = ReflectionUtils.getField(field, object);
            if (Objects.isNull(val)) {
                continue;
            }
            retMap.put(key, val);
        }
        return retMap;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = Lists.newArrayList();
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fieldArr = searchType.getDeclaredFields();
            if (fieldArr.length > 0) {
                fieldList.addAll(Arrays.asList(fieldArr));
            }
            searchType = searchType.getSuperclass();
        }
        return fieldList;
    }
}

