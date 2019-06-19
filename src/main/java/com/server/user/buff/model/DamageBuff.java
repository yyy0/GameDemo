package com.server.user.buff.model;

import com.server.tool.PacketSendUtil;
import com.server.tool.TimeUtil;
import com.server.user.fight.FightAccount;
import com.server.user.fight.packet.SM_Buff;

/**
 * @author yuxianming
 * @date 2019/6/18 14:48
 */
public class DamageBuff extends AbstractBuff {

    @Override
    public void doAction(FightAccount fightAccount) {
        this.setLastEffectTime(TimeUtil.now());
        int effectValue = getBuffResource().getEffectValue();
        long hp = fightAccount.getHp();
        long result = hp - effectValue;
        fightAccount.setHp(Math.max(0, result));
        SM_Buff packet = SM_Buff.valueOf(getBuffResource().getName(), fightAccount.getHp(), fightAccount.getMaxHP(), Math.min(hp, effectValue));
        PacketSendUtil.send(fightAccount.getAccountId(), packet);

    }
}
