package com.server.map.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.map.model.Grid;
import com.server.map.packet.CM_ChangeMap;
import com.server.map.packet.CM_MapInfo;
import com.server.map.packet.CM_Move;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/21 0:11
 */
@Component
@GmAnno(title = "地图gm")
public class MapGm {

    @GmMethod(name = "切换地图", param = "参数:地图id", clz = CM_ChangeMap.class)
    public void changeMap(Account account, Integer mapId) {
        SpringContext.getWorldService().changeMap(account, mapId);
    }


    @GmMethod(name = "移动坐标", param = "参数:x坐标 y坐标", clz = CM_Move.class)
    public void move(Account account, int gridX, int gridY) {
        Grid grid = Grid.valueOf(gridX, gridY);
        SpringContext.getWorldService().move(account, grid);
    }

    @GmMethod(name = "打印地图id", param = "参数:地图id", clz = CM_MapInfo.class)
    public void printMapInfo(Account account, int mapId) {
        SpringContext.getWorldService().printMapInfo(account, mapId);
    }
}
