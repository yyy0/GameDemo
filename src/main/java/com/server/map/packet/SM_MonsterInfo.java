package com.server.map.packet;

import com.server.monster.model.Monster;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/6/11 18:28
 */
public class SM_MonsterInfo implements Serializable {

    private List<Monster> monsters;

    private String mapName;

    public static SM_MonsterInfo valueOf(List<Monster> monsters, String mapName) {
        SM_MonsterInfo packet = new SM_MonsterInfo();
        packet.monsters = monsters;
        packet.mapName = mapName;
        return packet;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
