package com.alibaba.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)  //注解的生命周期
@Target(ElementType.METHOD)     //修饰于方法
@Documented     //被javadoc所记录
/**
 * @Author Zhs
 */
public @interface EagleEye {
    String doc() default "";
}

