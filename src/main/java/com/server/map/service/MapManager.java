package com.server.map.service;


import com.server.map.model.MapInfo;
import com.server.map.resource.MapResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/4/28 20:34
 */
@Component
public class MapManager {

    private Map<Integer, MapResource> mapResources = new HashMap<>();
    private Map<Integer, MapInfo> mapInfos = new HashMap<>();
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    /**
     * 初始化地图配置，地图info
     */
    public void initMap() {
        MapResource mapResource1 = MapResource.valueOf(1001, "新手村", 1, 1);
        MapResource mapResource2 = MapResource.valueOf(1002, "学徒村", 1, 1);
        MapResource mapResource3 = MapResource.valueOf(1003, "勇士村", 1, 1);
        mapResources.put(1001, mapResource1);
        mapResources.put(1002, mapResource2);
        mapResources.put(1003, mapResource3);
        mapInfos.put(1001, MapInfo.valueOf(1001));
        mapInfos.put(1002, MapInfo.valueOf(1002));
        mapInfos.put(1003, MapInfo.valueOf(1003));
    }

    public MapResource getResource(int id) {
        MapResource map = mapResources.get(id);
        return map;
    }

    @PostConstruct
    private final void init() {
        //instance.initMap();
        factory = Persistence.createEntityManagerFactory("PersistenceUnit");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }


    /**
     * 获取对应mapinfo
     */
    public MapInfo getMapInfo(int mapid) {
        return mapInfos.get(mapid);
    }
}
