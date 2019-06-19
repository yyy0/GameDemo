package com.server.user.skill.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.user.account.model.Account;
import com.server.user.skill.packet.*;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/12 10:48
 */
@Component
@GmAnno(title = "技能gm")
public class SkillGm {

    @GmMethod(name = "学习技能", param = "参数:技能id", clz = CM_StudySkill.class)
    public void studySkill(Account account, int skillId) {
        SpringContext.getSkillService().studySkill(account, skillId);
    }

    @GmMethod(name = "展示所有技能", clz = CM_AllSkill.class)
    public void printAllSkill(Account account) {
        SpringContext.getSkillService().printAllSkill(account);
    }

    @GmMethod(name = "展示技能栏信息", clz = CM_SkillSlot.class)
    public void printSkillSlot(Account account) {

        SpringContext.getSkillService().printSkillSlot(account);
    }

    @GmMethod(name = "技能栏移除", param = "参数:槽位", clz = CM_RemoveSkillFromSlot.class)
    public void removeSkillFromSlot(Account account, int index) {
        SpringContext.getSkillService().removeSkillFromSlot(account, index);
    }

    @GmMethod(name = "设置技能栏", param = "参数:技能id 槽位", clz = CM_SetSkillSlot.class)
    public void setSkillToSlot(Account account, int skillId, int index) {
        SpringContext.getSkillService().setSkillToSlot(account, skillId, index);
    }
}
