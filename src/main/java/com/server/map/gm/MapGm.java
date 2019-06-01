package com.server.map.gm;

import com.SpringContext;
import com.server.command.anno.GmAnno;
import com.server.command.anno.GmMethod;
import com.server.map.model.Grid;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/5/21 0:11
 */
@Component
@GmAnno(title = "地图gm")
public class MapGm {

    @GmMethod(name = "切换地图", param = "参数:地图id")
    public void changeMap(Account account, Integer mapId) {
        SpringContext.getWorldService().changeMap(account, mapId);
    }


    @GmMethod(name = "移动坐标", param = "参数:x坐标 y坐标")
    public void move(Account account, int gridX, int gridY) {
        Grid grid = Grid.valueOf(gridX, gridY);
        SpringContext.getWorldService().move(account, grid);
    }

    @GmMethod(name = "打印地图id", param = "参数:地图id")
    public void printMapInfo(int mapId) {
        SpringContext.getWorldService().printMapInfo(mapId);
    }
}
