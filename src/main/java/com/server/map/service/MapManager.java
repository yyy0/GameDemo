package com.server.map.service;


import com.server.common.resource.ResourceManager;
import com.server.map.model.MapInfo;
import com.server.map.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:34
 */
@Component
public class MapManager {


    private Logger logger = LoggerFactory.getLogger(MapManager.class);
    @Autowired
    private ResourceManager resourceManager;
    private Map<Integer, MapInfo> mapInfos = new HashMap<>();
    private Map<Integer, Object> mapResources = new HashMap<>();


    /**
     * 初始化地图信息
     */
    public void initMap() {
        mapResources = resourceManager.getResources(MapResource.class.getSimpleName());
        for (Map.Entry<Integer, Object> entry : mapResources.entrySet()) {
            int mapId = entry.getKey();
            MapResource resource = (MapResource) entry.getValue();
            mapInfos.put(mapId, MapInfo.valueOf(mapId, resource));
        }

    }

    /**
     * 获取对应mapInfo
     */
    public MapInfo getMapInfo(int mapId) {
        return mapInfos.get(mapId);
    }


    /**
     * 获取mapResource
     * @param id
     * @return
     */
    public MapResource getMapResource(int id) {
        MapResource resource = (MapResource) mapResources.get(id);
        if (resource == null) {
            logger.error("ItemResource找不到对应配置id：{0}" + id);
        }
        return resource;
    }
}

