package com.server.user.buff.constant;

import com.server.user.buff.model.AbstractBuff;
import com.server.user.buff.model.AddAtkBuff;
import com.server.user.buff.model.DamageBuff;

/**
 * @author yuxianming
 * @date 2019/6/18 12:30
 */
public enum BuffType {

    /**
     * 通用持续伤害buff
     */
    COMMON_DAMAGE(101, DamageBuff.class),

    ATK_BUFF(102, AddAtkBuff.class);

    /**
     * buffId
     */
    private int id;
    /**
     * buff类
     */
    private Class<? extends AbstractBuff> buffClz;

    BuffType(int id, Class<? extends AbstractBuff> clz) {
        this.id = id;
        this.buffClz = clz;
    }


    public static BuffType typeOf(int typeId) {
        for (BuffType type : values()) {
            if (type.id == typeId) {
                return type;
            }
        }
        throw new IllegalArgumentException("找不到对应buff类型ID:" + typeId);
    }

    public AbstractBuff create() {
        try {
            return this.buffClz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("生成buff实例错误：" + this.buffClz.getName());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<? extends AbstractBuff> getBuffClz() {
        return buffClz;
    }

    public void setBuffClz(Class<? extends AbstractBuff> buffClz) {
        this.buffClz = buffClz;
    }
}
