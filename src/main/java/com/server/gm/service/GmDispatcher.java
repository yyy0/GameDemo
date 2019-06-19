package com.server.gm.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/21 11:39
 */
@Component
public class GmDispatcher {

    /**
     * string  GM方法名称
     * GmDefinition  gm方法定义
     */
    private Map<String, GmDefinition> gmDefinitions = new HashMap<>();

    /**
     * string  GM模块名称
     * Map<String, GmDefinition>  对应gm模块下所有gm方法
     */
    private Map<String, Map<String, GmDefinition>> gmModelDefinitions = new HashMap<>();

    public void regGmDefinition(String methodName, GmDefinition gmDefinition) {
        GmDefinition definition = gmDefinitions.put(methodName, gmDefinition);
        if (definition != null) {
            throw new IllegalArgumentException("有重复的gm方法名称：" + methodName);
        }
    }

    public void regGmClassDefinition(String gmClassName, Map<String, GmDefinition> gmDefinitions) {
        gmModelDefinitions.put(gmClassName, gmDefinitions);
    }

    public GmDefinition getGmDefinition(String methodName) {
        return gmDefinitions.get(methodName);
    }

    public Map<String, GmDefinition> getModelDefinition(String className) {
        return gmModelDefinitions.get(className);
    }

    public Map<String, Map<String, GmDefinition>> getGmModelDefinitions() {
        return gmModelDefinitions;
    }

    public void setGmModelDefinitions(Map<String, Map<String, GmDefinition>> gmModelDefinitions) {
        this.gmModelDefinitions = gmModelDefinitions;
    }

    public int getModelPanelHeight() {
        int height = 0;
        for (Map.Entry<String, Map<String, GmDefinition>> entry : gmModelDefinitions.entrySet()) {
            height += 1 + entry.getValue().keySet().size();

        }
        return height;
    }

    public Map<String, GmDefinition> getGmDefinitions() {
        return gmDefinitions;
    }
}
