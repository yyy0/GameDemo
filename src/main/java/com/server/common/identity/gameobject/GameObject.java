package com.server.common.identity.gameobject;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/30 11:20
 */
public abstract class GameObject implements Serializable {

    protected Long objectId;

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
