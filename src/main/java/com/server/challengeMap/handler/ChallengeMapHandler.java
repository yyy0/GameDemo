package com.server.challengeMap.handler;

import com.SpringContext;
import com.server.map.constant.MapType;
import com.server.map.handler.AbstractMapHandler;
import com.server.map.model.Scene;
import com.server.monster.model.Monster;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/7/2 10:57
 */
@Component
public class ChallengeMapHandler extends AbstractMapHandler {
    @Override
    public MapType getMapType() {
        return MapType.CHALLENGE_DUNGEON;
    }


    @Override
    public void handleDeadMonster(Account account, Monster monster) {
        if (account == null || monster == null) {
            return;
        }
        Scene scene = getMapInfo(account, 0);
        SpringContext.getChallengeMapService().handlerDeadMonster(account, monster, scene);
    }

    @Override
    public void leaveMap(Account account) {
        SpringContext.getChallengeMapService().leaveMap(account);
    }

    @Override
    public void enterMap(Account account, int mapId) {
        SpringContext.getChallengeMapService().enterMap(account, mapId);
    }

    @Override
    public Scene getMapInfo(Account account, int mapId) {
        Map<String, Scene> mapInfoMap = SpringContext.getChallengeMapService().getMapInfos();
        if (mapId == 0) {
            return mapInfoMap.get(account.getAccountId());
        } else {
            for (Scene scene : mapInfoMap.values()) {
                if (scene.getMapId() == mapId) {
                    return scene;
                }
            }
            return null;
        }
    }
}
