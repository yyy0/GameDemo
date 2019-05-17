package com.server.user.item.constant;

import com.server.user.attribute.model.Equipment;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.model.CommonItem;
import com.server.user.item.model.Medicine;
import com.server.user.item.model.Pandora;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuxianming
 * @date 2019/5/10 18:15
 */
public enum ItemType {


    /**
     * 普通道具
     */
    COMMONITEM(1, CommonItem.class),
    /**
     * 药品
     */
    MEDICINE(2, Medicine.class),
    /**
     * 装备
     */
    EQUIPMENT(3, Equipment.class),
    /**
     * 潘多拉宝箱
     */
    PANDDORA(4, Pandora.class);

    public static final Logger logger = LoggerFactory.getLogger(ItemType.class);

    /**
     * 道具类型id
     */
    private int id;
    /**
     * 道具实体类
     */
    private Class<? extends AbstractItem> itemClazz;

    ItemType(int id, Class<? extends AbstractItem> itemClazz) {
        this.id = id;
        this.itemClazz = itemClazz;
    }

    /**
     * 生成实例道具
     *
     * @return
     */
    public AbstractItem create() {
        try {
            return itemClazz.newInstance();
        } catch (Exception e) {
            logger.error("生成道具实例错误" + e);
            throw new IllegalArgumentException("生成道具实例错误");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<? extends AbstractItem> getItemClazz() {
        return itemClazz;
    }

    public void setItemClazz(Class<? extends AbstractItem> itemClazz) {
        this.itemClazz = itemClazz;
    }
}
