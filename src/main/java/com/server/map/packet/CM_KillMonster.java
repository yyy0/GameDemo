package com.server.map.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/6/11 22:01
 */
public class CM_KillMonster implements Serializable {

    /**
     * 怪物唯一id
     */
    private long monsterGid;

    public static CM_KillMonster valueOf(long monsterGid) {
        CM_KillMonster packet = new CM_KillMonster();
        packet.monsterGid = monsterGid;
        return packet;
    }

    public long getMonsterGid() {
        return monsterGid;
    }

    public void setMonsterGid(long monsterGid) {
        this.monsterGid = monsterGid;
    }
}
