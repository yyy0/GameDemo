package com.client.dispatcher;

import java.lang.annotation.*;

/**
 * @author yuxianming
 * @date 2019/5/17 16:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ClientHandlerAnno {
}
