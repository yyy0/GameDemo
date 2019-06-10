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
    /**
     * gm方法名称
     */
    public String name() default "gm方法";

    /** gm参数说明 */
    public String param() default "";

    /** gm使用说明 */
    public String des() default "";

    /**
     * gm关联的协议class
     */
    public Class clz();
}
