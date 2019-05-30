package com.server.common.event.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuxianming
 * @date 2019/5/29 11:12
 */
@Component
public class EventBusManager {

    private static final Logger logger = LoggerFactory.getLogger(EventBusManager.class);

    private static final int EXECUTOR_SIZE = 2;
    private ExecutorService[] executorServices;

    private Map<Class<?>, List<ReceiverDefinition>> receiverDefinitions = new HashMap<>();

    @PostConstruct
    private void init() {
        executorServices = new ExecutorService[EXECUTOR_SIZE];
        for (int i = 0; i < EXECUTOR_SIZE; i++) {

            executorServices[i] = Executors.newSingleThreadExecutor();
        }
    }

    /**
     * 同步提交事件
     *
     * @param event
     */
    public void syncSubmit(Object event) {
        this.doSubmitEvent(event);
    }

    /**
     * 异步提交事件
     *
     * @param event
     */
    public void asyncSubmit(Object event) {
        this.executorServices[new Random().nextInt(2)].submit(() -> doSubmitEvent(event));

    }

    private void doSubmitEvent(Object event) {
        List definitions = getDefinitions(event);

        if (definitions != null) {
            Iterator iterator = definitions.iterator();
            while (iterator.hasNext()) {
                ReceiverDefinition definition = (ReceiverDefinition) iterator.next();
                definition.invoke(event);
            }
        }
    }

    private List<ReceiverDefinition> getDefinitions(Object event) {
        Class clz = event.getClass();
        List definitions = receiverDefinitions.get(clz);
        if (definitions == null || definitions.isEmpty()) {
            logger.warn("找不到receiverDefinition：" + clz);
        }
        return definitions;
    }


    public void regReceiver(Class<?> clz, ReceiverDefinition definition) {
        if (!receiverDefinitions.containsKey(clz)) {
            receiverDefinitions.put(clz, new ArrayList<ReceiverDefinition>());
        }

        if (!receiverDefinitions.get(clz).contains(definition)) {
            receiverDefinitions.get(clz).add(definition);
        }
    }

}
