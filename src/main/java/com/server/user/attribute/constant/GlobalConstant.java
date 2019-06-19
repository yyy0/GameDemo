package com.server.user.attribute.constant;

import java.text.DecimalFormat;

/**
 * @author yuxianming
 * @date 2019/6/5 10:41
 */
public interface GlobalConstant {

    static DecimalFormat df = new DecimalFormat("0.00%");

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

    /**
     * 转成百分比显示
     *
     * @param value
     * @return
     */
    static String getPercentage(double value) {
        return df.format(getRatio(value));
    }
}
