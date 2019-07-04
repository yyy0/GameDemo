package com.server.challengeMap;

import com.SpringContext;
import com.server.common.command.ICommand;
import com.server.common.command.LeaveSceneNotifyCommand;
import com.server.map.model.MapInfo;
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

    public MapInfo getMapInfos(String accountId) {
        return challengeMapManager.getMapInfo(accountId);
    }

    public void handlerDeadMonster(Account account, Monster monster, MapInfo mapInfo) {

        if (monster != null) {
            mapInfo.removeMonster(monster.getObjectId());
        }

        // 是否已清怪
        if (!mapInfo.isEmptyMonster()) {
            return;
        }
        // 若全部怪物死亡则 通关发奖 踢人
        Map<Integer, Integer> itemsMap = SpringContext.getWorldService().getMapResource(mapInfo.getMapId()).getReward();
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
        SpringContext.getSceneExecutorService().submit(notifyCommand);

    }

    /**
     * 个人副本地图 离开地图
     * 清除战斗账号 清除manger对应的个人副本
     */
    public void leaveMap(Account account) {
        MapInfo mapInfo = challengeMapManager.getMapInfo(account.getAccountId());
        mapInfo.removeFightAccount(account.getAccountId());
        //离开地图时 去掉延迟关闭副本任务
        Map<Class<? extends ICommand>, ICommand> commandMap = mapInfo.getCommandMap();
        for (ICommand command : commandMap.values()) {
            command.cancel();
        }
        challengeMapManager.removeMapInfo(account.getAccountId());

    }


    public void enterMap(Account account, int targetMapId) {
        MapResource mapResource = SpringContext.getWorldService().getMapResource(targetMapId);
        MapInfo mapInfo = MapInfo.valueOf(targetMapId, mapResource);

        mapInfo.initMonster();
        int gridX = mapResource.getBornX();
        int gridY = mapResource.getBornY();

        account.setOldMapId(account.getMapId());
        account.setMapId(targetMapId);
        account.setGridX(gridX);
        account.setGirdY(gridY);
        FightAccount fightAccount = FightAccount.valueOf(account);
        mapInfo.addFightAccount(fightAccount);
        account.setFightAccount(fightAccount.copy());


        logger.info("账号[{}]进入地图：{}", account.getName(), mapResource.getName());

        // 添加延迟command， 如果副本超时 强制踢人
//        LeaveSceneDelayCommand delayCommand = new LeaveSceneDelayCommand(account.getAccountId(),
//                                                    account.getMapId(), mapResource.getDuration(),
//                                                    account.getOldMapId(), account);
//        mapInfo.addCommand(delayCommand);
        LeaveSceneNotifyCommand notifyCommand = new LeaveSceneNotifyCommand(account.getAccountId(),
                account.getMapId(), mapResource.getDuration(), 5000,
                account, account.getOldMapId());
        SpringContext.getSceneExecutorService().submit(notifyCommand);
        challengeMapManager.addMapInfo(account.getAccountId(), mapInfo);

//        SpringContext.getAccountService().saveAccountInfo(account.getAccountId());
        SpringContext.getAccountService().saveAccountInfo(account);
        SM_ChangeMap packet = SM_ChangeMap.valueOf(account.getAccountId(), targetMapId, account.getOldMapId());
        PacketSendUtil.send(account, packet);


        SM_MapInfo mapInfoPacket = SM_MapInfo.valueOf(mapInfo);
        PacketSendUtil.send(account, mapInfoPacket);
    }

    public Map<String, MapInfo> getMapInfos() {
        return challengeMapManager.getMapInfos();
    }

}
