package com.server.gm.anno;

import java.lang.annotation.*;

/**
 * @author yuxianming
 * @date 2019/5/21 0:05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GmAnno {
    public String title();
}
