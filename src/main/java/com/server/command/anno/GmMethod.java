package com.server.command.anno;

import java.lang.annotation.*;

/**
 * @author yuxianming
 * @date 2019/5/21 0:16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GmMethod {
    public String name() default "gm方法";

    public String param() default "";

    public String des() default "";
}
