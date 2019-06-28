package com.server.task.constant;

/**
 * 完成任务条件类型
 *
 * @author yuxianming
 * @date 2019/6/25 14:25
 */
public enum TaskConditionType {

    /**
     * 穿戴X件装备  完成条件配置：累计穿戴件数
     */
    EQUIP_NUM,

    /**
     * 总计装备阶数 完成条件配置：累计装备阶数
     */
    EQUIP_TOTAL_UPGRADE,

    /**
     * 通关某个副本 完成条件配置：副本id
     */
    COMPLETE_DUNGEON
}
