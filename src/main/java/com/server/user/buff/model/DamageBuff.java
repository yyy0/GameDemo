package com.server.user.buff.model;

import com.server.monster.model.Monster;
import com.server.tool.PacketSendUtil;
import com.server.user.fight.FightAccount;
import com.server.user.fight.packet.SM_Buff;

/**
 * @author yuxianming
 * @date 2019/6/18 14:48
 */
public class DamageBuff extends AbstractBuff {

    @Override
    public void doAction(FightAccount fightAccount) {

        int effectValue = getBuffResource().getEffectValue();
        long hp = fightAccount.getHp();
        long result = hp - effectValue;
        fightAccount.setHp(Math.max(0, result));
        SM_Buff packet = SM_Buff.valueOf(getBuffResource().getName(), fightAccount.getHp(), fightAccount.getMaxHP(), Math.min(hp, effectValue));
        PacketSendUtil.send(fightAccount.getAccountId(), packet);

    }

    @Override
    public void doAction(Monster monster) {
        int effectValue = getBuffResource().getEffectValue();
        long hp = monster.getCurHp();
        long result = hp - effectValue;
        monster.setCurHp(Math.max(0, result));

    }
}
