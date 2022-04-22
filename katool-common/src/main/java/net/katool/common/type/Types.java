package net.katool.common.type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author hongchen.cao
 * @since 09 四月 2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Types {
    public static ParameterizedType generateParameterizedType(Type rawType, Type... typeArguments) {
        return new ParameterizedTypeImpl(null, rawType, typeArguments);
    }
}
