package com.server.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/6 20:20
 */
public class MapUtils {

    public static <K1, K2> Map<K1, K2> deepCopy(Map<K1, K2> original) {
        Map<K1, K2> copy = new HashMap<K1, K2>();
        for (Map.Entry<K1, K2> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;

    }
}
