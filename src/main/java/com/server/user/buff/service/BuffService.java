package com.server.user.buff.service;

import com.server.common.resource.ResourceManager;
import com.server.log.LoggerUtil;
import com.server.user.buff.model.AbstractBuff;
import com.server.user.buff.resource.BuffResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/6/18 11:24
 */
@Component
public class BuffService {

    @Autowired
    private ResourceManager resourceManager;

    public BuffResource getBuffResource(int buffId) {
        Map<Integer, Object> buffResources = resourceManager.getResources(BuffResource.class.getSimpleName());
        BuffResource resource = (BuffResource) buffResources.get(buffId);
        if (resource == null) {
            LoggerUtil.error("BuffResource找不到对应配置id：{0}" + buffId);
        }
        return resource;
    }

    public AbstractBuff createBuff(int buffId) {
        BuffResource buffResource = getBuffResource(buffId);
        return createBuff(buffResource);
    }

    public AbstractBuff createBuff(BuffResource buffResource) {
        AbstractBuff abstractBuff = buffResource.getType().create();
        if (abstractBuff == null) {
            return null;
        }
        abstractBuff.init(buffResource);
        return abstractBuff;
    }
}
