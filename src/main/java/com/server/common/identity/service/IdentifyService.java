package com.server.common.identity.service;

import com.server.common.entity.CommonManager;
import com.server.common.identity.entity.IdentifyEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yuxianming
 * @date 2019/5/30 11:23
 */
@Component
public class IdentifyService {

    public enum IdentifyType {
        /**
         * 账号
         */
        ACCOUNT(true),
        /**
         * 道具
         */
        ITEM(true),
        ;

        private final boolean save;

        private IdentifyType(boolean save) {
            this.save = save;
        }

        public boolean isSave() {
            return save;
        }
    }

    private static final AtomicLong index = new AtomicLong(1000);

    @Autowired
    private CommonManager<String, IdentifyEnt> commonManager;

    public long getNextIdentify(IdentifyType type) {
        if (!type.isSave()) {
            return index.incrementAndGet();
        }
        IdentifyEnt ent = getOrCreateEnt(type.name());
        synchronized (ent) {
            long old = ent.getValue();
            long result = ent.getNextIdentify();
            long now = ent.getValue();
            if (now != old) {
                commonManager.update();
            }
            return result;
        }
    }

    public IdentifyEnt getOrCreateEnt(String id) {
        IdentifyEnt ent = commonManager.getEnt(IdentifyEnt.class, id);
        if (ent == null) {
            ent = IdentifyEnt.valueOf(id, createInitValue());
            commonManager.createEnt(ent);
            ent = commonManager.getEnt(IdentifyEnt.class, id);
        }

        return ent;
    }

    private long createInitValue() {
        long id = 1;
        return id;
    }

}
