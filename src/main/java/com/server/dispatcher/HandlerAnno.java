package com.server.dispatcher;

import java.lang.annotation.*;

/**
 * @author yuxianming
 * @date 2019/5/7 17:45
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandlerAnno {
}
