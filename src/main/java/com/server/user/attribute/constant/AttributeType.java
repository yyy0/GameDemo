package com.server.user.attribute.constant;

import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.model.Attribute;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/13 18:25
 * 属性类型
 */
public enum AttributeType implements Serializable {

    /**
     * 生命上限
     */
    MAX_HP(1, "生命上限") {
        @Override
        public AttributeType[] getCalculateRateAttribute() {
            return new AttributeType[]{HP_RATE};
        }
    },
    /**
     * 攻击力
     */
    ATTACK(2, "攻击力") {
        @Override
        public AttributeType[] getCalculateRateAttribute() {
            return new AttributeType[]{ATK_RATE};
        }
    },
    /**
     * 防御力
     */
    DEFENCE(3, "防御力") {
        @Override
        public AttributeType[] getCalculateRateAttribute() {
            return new AttributeType[]{DEFENCE_RATE};
        }
    },
    /**
     * 移动速度
     */
    MOVESPEED(4, "移动速度"),
    /**
     * 命中
     */
    HIT(5, "命中"),
    /**
     * 闪避
     */
    DOGE(6, "闪避"),
    /**
     * 生命值比例
     */
    HP_RATE(7, "生命值比例") {
        @Override
        public boolean isRateAttribute() {
            return true;
        }
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{MAX_HP};
        }
    },
    /**
     * 攻击力比例
     */
    ATK_RATE(8, "攻击力比例") {
        @Override
        public boolean isRateAttribute() {
            return true;
        }
        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{ATTACK};
        }
    },
    /**
     * 防御力比例
     */
    DEFENCE_RATE(9, "防御力比例") {
        @Override
        public boolean isRateAttribute() {
            return true;
        }

        @Override
        public AttributeType[] getEffectAttributes() {
            return new AttributeType[]{DEFENCE};
        }
    },
    /**
     * 力量
     */
    STRENGTH(10, "力量"),
    /**
     * 体力
     */
    VITALITY(11, "体力"),
    /**
     * 敏捷
     */
    AGILITY(12, "敏捷"),
    ;
    /**
     * 属性id
     */
    private int id;
    /**
     * 属性描述
     */
    private String desc;

    AttributeType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static AttributeType typeOf(int id) {
        for (AttributeType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new RuntimeException("找不到匹配的属性类型:" + id);
    }

    /**
     * 是否为万分比加成属性 显示转换用
     *
     * @return
     */
    public boolean isRateAttribute() {
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取受到影响的属性
     *
     * @return
     */
    public AttributeType[] getEffectAttributes() {
        return null;
    }

    /**
     * 获取对该属性有百分比加成的属性
     *
     * @return
     */
    public AttributeType[] getCalculateRateAttribute() {
        return null;
    }

    /**
     * 计算属性最终值
     *
     * @param attributes
     * @return
     */
    public long compute(Map<AttributeType, Attribute> attributes) {
        long value = AttributeUtil.getAttributeValue(attributes, this);
        long rate = AttributeUtil.getAttributeRate(attributes, this);
        return (long) Math.max(0, value * (1 + GlobalConstant.getRatio(rate)));
    }
}
