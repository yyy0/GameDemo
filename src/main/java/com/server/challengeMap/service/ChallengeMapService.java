package com.server.challengeMap.service;

import com.SpringContext;
import com.server.common.command.ICommand;
import com.server.common.command.LeaveSceneNotifyCommand;
import com.server.map.model.Scene;
import com.server.map.packet.SM_ChangeMap;
import com.server.map.packet.SM_MapInfo;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;
import com.server.user.item.model.AbstractItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 个人挑战副本
 *
 * @author yuxianming
 * @date 2019/7/2 10:58
 */
@Component
public class ChallengeMapService {

    public static Logger logger = LoggerFactory.getLogger(ChallengeMapService.class);

    @Autowired
    private ChallengeMapManager challengeMapManager;

    public Scene getMapInfos(String accountId) {
        return challengeMapManager.getMapInfo(accountId);
    }

    public void handlerDeadMonster(Account account, Monster monster, Scene scene) {

        if (monster != null) {
            scene.removeMonster(monster.getObjectId());
        }

        // 是否已清怪
        if (!scene.isEmptyMonster()) {
            return;
        }
        // 若全部怪物死亡则 通关发奖 踢人
        Map<Integer, Integer> itemsMap = SpringContext.getWorldService().getMapResource(scene.getMapId()).getReward();
        //发奖
        if (itemsMap != null) {
            List<AbstractItem> items = SpringContext.getStoreService().createItems(itemsMap);
            if (SpringContext.getStoreService().isEnoughPackSize(account, items)) {
                SpringContext.getStoreService().addItemsToBag(account, items);
            }
        }

        // 通知离开副本 并延时退出
        LeaveSceneNotifyCommand notifyCommand = new LeaveSceneNotifyCommand(account.getAccountId(),
                account.getMapId(), 0, 5000,
                account, account.getOldMapId());
        scene.addCommand(notifyCommand);
        SpringContext.getSceneExecutorService().submit(notifyCommand);

    }

    /**
     * 个人副本地图 离开地图
     * 清除战斗账号 清除manger对应的个人副本
     */
    public void leaveMap(Account account) {
        Scene scene = challengeMapManager.getMapInfo(account.getAccountId());
        scene.removeFightAccount(account.getAccountId());
        //离开地图时 去掉延迟关闭副本任务
        Map<Class<? extends ICommand>, ICommand> commandMap = scene.getCommandMap();
        for (ICommand command : commandMap.values()) {
            command.cancel();
        }
        challengeMapManager.removeMapInfo(account.getAccountId());
    }


    public void enterMap(Account account, int targetMapId) {
        MapResource mapResource = SpringContext.getWorldService().getMapResource(targetMapId);
        Scene scene = Scene.valueOf(targetMapId, mapResource);

        scene.initMonster();
        int gridX = mapResource.getBornX();
        int gridY = mapResource.getBornY();

        //设置新地图信息
        account.setOldMapId(account.getMapId());
        account.setMapId(targetMapId);
        account.setGridX(gridX);
        account.setGirdY(gridY);
        FightAccount fightAccount = FightAccount.valueOf(account);
        scene.addFightAccount(fightAccount);
        account.setFightAccount(fightAccount.copy());

        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());
        LeaveSceneNotifyCommand notifyCommand = new LeaveSceneNotifyCommand(account.getAccountId(),
                account.getMapId(), mapResource.getDuration(), 5000,
                account, account.getOldMapId());
        scene.addCommand(notifyCommand);
        SpringContext.getSceneExecutorService().submit(notifyCommand);
        challengeMapManager.addMapInfo(account.getAccountId(), scene);

        SpringContext.getAccountService().saveAccountInfo(account);
        SM_ChangeMap packet = SM_ChangeMap.valueOf(account.getAccountId(), targetMapId, account.getOldMapId());
        PacketSendUtil.send(account, packet);

        SM_MapInfo mapInfoPacket = SM_MapInfo.valueOf(scene);
        PacketSendUtil.send(account, mapInfoPacket);
    }

    public Map<String, Scene> getMapInfos() {
        return challengeMapManager.getMapInfos();
    }

}
