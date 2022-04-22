package net.katool.common.type;

import com.google.common.base.Preconditions;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author hongchen.cao
 * @since 09 四月 2022
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;

    public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
        if (rawType instanceof Class) {
            Class<?> rawTypeAsClass = (Class<?>)rawType;
            boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
            Preconditions.checkArgument(ownerType != null || isStaticOrTopLevelClass);
        }

        this.ownerType = ownerType == null ? null : $Gson$Types.canonicalize(ownerType);
        this.rawType = $Gson$Types.canonicalize(rawType);
        this.typeArguments = (Type[])typeArguments.clone();
        int t = 0;

        for(int length = this.typeArguments.length; t < length; ++t) {
            Preconditions.checkNotNull(this.typeArguments[t]);
            checkNotPrimitive(this.typeArguments[t]);
            this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
        }

    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.typeArguments.clone();
    }

    @Override
    public Type getRawType() {
        return this.rawType;
    }

    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }
    
    static void checkNotPrimitive(Type type) {
        Preconditions.checkArgument(!(type instanceof Class) || !((Class<?>)type).isPrimitive());
    }
}
