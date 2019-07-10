package com.server.map.service;


import com.server.common.resource.ResourceManager;
import com.server.map.constant.MapType;
import com.server.map.model.Scene;
import com.server.map.resource.MapResource;
import com.server.monster.model.Monster;
import com.server.monster.resource.MonsterAreaResource;
import com.server.monster.resource.MonsterResource;
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

    private Map<Integer, Scene> mapInfos = new HashMap<>();
    private Map<Integer, Object> mapResources = new HashMap<>();
    private Map<Integer, Object> monsterResources = new HashMap<>();


    /**
     * 初始化地图信息
     */
    public void initMap() {
        mapResources = resourceManager.getResources(MapResource.class.getSimpleName());
        monsterResources = resourceManager.getResources(MonsterResource.class.getSimpleName());
        Map<Integer, Object> monsterAreaResource = resourceManager.getResources(MonsterAreaResource.class.getSimpleName());
        for (Map.Entry<Integer, Object> entry : mapResources.entrySet()) {
            int mapId = entry.getKey();
            MapResource resource = (MapResource) entry.getValue();
            if (resource == null) {
                continue;
            }
            if (resource.getType() != MapType.COMMON_MAP) {
                continue;
            }
            Scene scene = Scene.valueOf(mapId, resource);

            //加载刷怪 放入地图中
            for (Object object : monsterAreaResource.values()) {

                MonsterAreaResource areaResource = (MonsterAreaResource) object;
                if (areaResource.getMapId() != scene.getMapId()) {
                    continue;
                }
                int monsterId = areaResource.getMonsterId();
                MonsterResource monsterResource = (MonsterResource) monsterResources.get(monsterId);
                Monster monster = Monster.valueOf(monsterResource);
                monster.setGridX(areaResource.getGridX());
                monster.setGridY(areaResource.getGridY());
                //将怪物放入地图信息中
                scene.addMonster(monster);

            }

            mapInfos.put(mapId, scene);
        }

    }

    /**
     * 获取对应mapInfo
     */
    public Scene getMapInfo(int mapId) {
        return mapInfos.get(mapId);
    }

    public Map<Integer, Scene> getMapInfos() {
        return mapInfos;
    }

    /**
     * 获取mapResource
     *
     * @param id
     * @return
     */
    public MapResource getMapResource(int id) {
        MapResource resource = (MapResource) mapResources.get(id);
        if (resource == null) {
            logger.error("MapResource找不到对应配置id：" + id);
        }
        return resource;
    }


}

