package com.server.map.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.map.model.Grid;
import com.server.map.packet.*;
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
        int mapId = req.getMapId();
        int id = mapId == 0 ? account.getMapId() : mapId;
        SpringContext.getWorldService().printMapInfo(account, id);
    }

    /**
     * 打印指定地图怪物信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printMonstersInfo(TSession session, CM_MonsterInfo req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getWorldService().printMonstersInfo(account, req.getMapId());
    }

    /**
     * 击杀指定怪物 唯一id
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void killMonster(TSession session, CM_KillMonster req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getWorldService().killMonster(account, req.getMonsterGid());
    }

    @HandlerAnno
    public void hitMonster(TSession session, CM_HitMonster req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getWorldService().hitMonster(account, req.getSkillId(), req.getMonsterGid());
    }
}
