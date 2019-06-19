package com.server.user.fight.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.fight.packet.CM_UseSkill;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/14 18:18
 */
@GmAnno(title = "战斗gm")
@Component
public class FightGm {

    @GmMethod(name = "使用技能", param = "参数:目标账号 技能id ", clz = CM_UseSkill.class)
    public void useSkill(Account account, String targetAccountId, int skillId) {
        SpringContext.getFightService().useSkill(account, targetAccountId, skillId);
    }
}
