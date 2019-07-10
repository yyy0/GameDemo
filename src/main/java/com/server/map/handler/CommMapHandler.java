package com.server.map.handler;

import com.SpringContext;
import com.server.map.constant.MapType;
import com.server.map.model.Scene;
import com.server.monster.model.Monster;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;


/**
 * @author yuxianming
 * @date 2019/7/2 19:03
 */
@Component
public class CommMapHandler extends AbstractMapHandler {
    @Override
    public MapType getMapType() {
        return MapType.COMMON_MAP;
    }

    @Override
    public Scene getMapInfo(Account account, int mapId) {
        int id = mapId == 0 ? account.getMapId() : mapId;
        return SpringContext.getMapManager().getMapInfo(id);
    }

    @Override
    public void handleDeadMonster(Account account, Monster monster) {
        SpringContext.getWorldService().handlerDeadMonster(account, monster);
    }
}
