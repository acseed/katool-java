package net.katool.common.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于对象转换成map的时候
 * @author hongchen.cao
 * @since 30 三月 2021
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapKey {
    String value() default StringUtils.EMPTY;
    
    boolean ignore() default false;
}
