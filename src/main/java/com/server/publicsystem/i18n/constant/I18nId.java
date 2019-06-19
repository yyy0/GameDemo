package com.server.publicsystem.i18n.constant;

/**
 * @author yuxianming
 * @date 2019/5/17 11:45
 */
public interface I18nId {

    //操作成功
    int OPERA_SUCCESS = 1;
    //操作失败
    int OPERA_FAIL = 2;
    //等级不够
    int LELVEL_LIMIT = 3;
    //gm命令错误
    int GMCOMMAND_ERROR = 4;
    //非法数值
    int ILLEGAL_VALUE = 5;
    //未达到条件
    int CONDITION_FAIL = 6;
    //道具材料不足
    int ITEM_NOT_ENOUGH = 7;
    //背包空间不够
    int BAG_NOT_ENOUGH = 8;
    //该装备不存在
    int EQUIPMENT_NOT_EXIST = 9;
    //装备与部位不匹配
    int EQUIPMENT_NOT_MATCH = 10;
    //该部位没有装备
    int POSITION_NO_EQUIPMENT = 11;
    //该账号不存在
    int ACCOUNT_NOT_EXIST = 12;
    //密码错误
    int WRONG_PASSWORD = 13;
    //穿戴等级不足
    int EQUIP_LEVEL_LIMIT = 14;
    //背包暂无道具
    int BAG_NULL_ITEMS = 15;
    //仓库暂无道具
    int WAREHOUSE_NULL_ITEMS = 16;
    //该装备部位不可升阶
    int EQUIP_POSITION_UPGRADE_LIMIT = 17;
    //该装备部位已满阶
    int EQUIP_GRADE_MAX = 18;
    //背包没有该道具
    int BAG_NO_ITEM = 19;
    //仓库没有该道具
    int WAREHOUSE_NO_ITEM = 20;
    //该坐标无法行走
    int GRID_CAN_NOT_WALK = 21;
    //当前地图没有怪物
    int MAP_NULL_MONSTER = 22;
    //该怪物不存在
    int NO_MONSTER = 23;
    //该怪物掉落为空
    int MONSTER_NULL_DROP = 24;
    //已学过该技能
    int SKILL_ALREADY_ACTIVE = 25;
    //该技能尚未学习
    int SKILL_NOT_ACTIVE = 26;
    //该角色暂无技能
    int ACCOUNT_NULL_SKILL = 27;
    //技能冷却中
    int SKILL_CD_NOW = 28;
    //魔法值不足
    int NO_MP = 29;
    //攻击目标不在范围内
    int TARGET_NOT_IN_RANGE = 30;
    //攻击目标已死亡
    int TARGET_ALREADY_DEATH = 31;
}
