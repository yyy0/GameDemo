package com.server.user.attribute.constant;

/**
 * @author yuxianming
 * @date 2019/6/5 10:41
 */
public interface GlobalConstant {

    /**
     * 万分比
     */
    int RATIO_VALUE = 10000;

    /**
     * 获取比例值（为了显示小数，直接用int会丢失小数点后面的数字）
     *
     * @param value
     * @return
     */
    static double getRatio(double value) {
        return value * 1.0d / GlobalConstant.RATIO_VALUE;
    }
}
