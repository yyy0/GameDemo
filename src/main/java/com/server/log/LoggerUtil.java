package com.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuxianming
 * @date 2019/6/15 20:40
 */
public class LoggerUtil {

    private static final String CONSOLE_SYSTEM = "console";

    private static final Logger LOGGER = LoggerFactory.getLogger(CONSOLE_SYSTEM);

    public static void error(String str, Object... args) {
        LOGGER.error(str, args);
    }

    public static void error(String str) {
        LOGGER.error(str);
    }

    public static void info(String str) {
        LOGGER.info(str);
    }

    public static void info(String str, Object... args) {
        LOGGER.info(str, args);
    }
}
