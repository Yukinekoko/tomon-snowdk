package com.snowmeow.tomonsdk.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface OnMessage {

    String value();

    String help() default "";

    Type type() default Type.FULL_MATCH;

    enum Type{FULL_MATCH, PREFIX, REGEX};

}
