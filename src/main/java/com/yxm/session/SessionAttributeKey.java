package com.yxm.session;

import io.netty.util.AttributeKey;

/**
 * @author yuxianming
 * @date 2019/5/7 21:43
 */
public class SessionAttributeKey {


    public static final AttributeKey<TSession> SESSION = AttributeKey.newInstance("SESSION");
}
