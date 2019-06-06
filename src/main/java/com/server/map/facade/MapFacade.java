package com.server.map.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.map.model.Grid;
import com.server.map.packet.CM_ChangeMap;
import com.server.map.packet.CM_MapInfo;
import com.server.map.packet.CM_Move;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/6 11:26
 */
@Component
public class MapFacade {


    /**
     * 切换地图
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void changeMap(TSession session, CM_ChangeMap req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getWorldService().changeMap(account, req.getMapId());
    }


    /**
     * 移动坐标
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void move(TSession session, CM_Move req) {
        Account account = SessionUtil.getAccountBySession(session);
        Grid grid = Grid.valueOf(req.getGridX(), req.getGirdY());
        SpringContext.getWorldService().move(account, grid);
    }


    /**
     * 打印地图信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printMapInfo(TSession session, CM_MapInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getWorldService().printMapInfo(account, req.getMapId());
    }
}
