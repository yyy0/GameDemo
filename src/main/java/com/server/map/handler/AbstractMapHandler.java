package com.server.map.handler;

import com.SpringContext;
import com.server.map.constant.MapType;
import com.server.map.model.Scene;
import com.server.monster.model.Monster;
import com.server.user.account.model.Account;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/7/1 21:12
 */
public abstract class AbstractMapHandler {

    public static Map<MapType, AbstractMapHandler> mapHandlerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        mapHandlerMap.put(getMapType(), this);
    }

    public static AbstractMapHandler getMapHandler(MapType type) {
        return mapHandlerMap.get(type);
    }

    public void enterMap(Account account, int mapId) {
        SpringContext.getWorldService().enterMap(account, mapId);
    }

    public void leaveMap(Account account) {
        SpringContext.getWorldService().leaveMap(account);
    }

    /**
     * 获取地图类型
     *
     * @return
     */
    public abstract MapType getMapType();

    public void handleDeadMonster(Account account, Monster monster) {

    }

    public Scene getMapInfo(Account account, int mapId) {
        int id = mapId == 0 ? account.getMapId() : mapId;
        return SpringContext.getMapManager().getMapInfo(id);
    }
}
