package com.server.challengeMap;

import com.server.common.resource.ResourceManager;
import com.server.map.model.MapInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/7/1 23:42
 */
@Component
public class ChallengeMapManager {

    private Logger logger = LoggerFactory.getLogger(ChallengeMapManager.class);

    @Autowired
    private ResourceManager resourceManager;

    /**
     * 账号id  mapInfo
     */
    private Map<String, MapInfo> mapInfos = new HashMap<>();
    private Map<Integer, Object> mapResources = new HashMap<>();
    private Map<Integer, Object> monsterResources = new HashMap<>();

    public MapInfo getMapInfo(String accountId) {
        return mapInfos.get(accountId);
    }


    public void removeMapInfo(String accountId) {
        mapInfos.remove(accountId);
    }

    public void addMapInfo(String accountId, MapInfo mapInfo) {
        if (accountId == null || mapInfo == null) {
            return;
        }
        mapInfos.put(accountId, mapInfo);
    }

    public Map<String, MapInfo> getMapInfos() {
        return mapInfos;
    }
}
