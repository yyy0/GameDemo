package com.server.map.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.map.model.Grid;
import com.server.map.packet.*;
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

    @GmMethod(name = "怪物信息", param = "参数:地图id", clz = CM_MonsterInfo.class)
    public void printMonstersInfo(Account account, int mapId) {
        SpringContext.getWorldService().printMonstersInfo(account, mapId);
    }

    @GmMethod(name = "击杀怪物", param = "参数:唯一id", clz = CM_KillMonster.class)
    public void killMonster(Account account, long gid) {
        SpringContext.getWorldService().killMonster(account, gid);
    }
}
