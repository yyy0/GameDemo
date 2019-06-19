package com.server.user.skill.facade;

import com.SpringContext;
import com.server.dispatcher.HandlerAnno;
import com.server.session.SessionUtil;
import com.server.session.model.TSession;
import com.server.user.account.model.Account;
import com.server.user.skill.packet.*;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/12 11:32
 */
@Component
public class SkillFacade {

    /**
     * 学习技能
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void studySkill(TSession session, CM_StudySkill req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getSkillService().studySkill(account, req.getSkillId());
    }


    /**
     * 显示所有技能
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printAllSkill(TSession session, CM_AllSkill req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getSkillService().printAllSkill(account);
    }

    /**
     * 展示技能栏信息
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void printSkillSlot(TSession session, CM_SkillSlot req) {
        Account account = SessionUtil.getAccountBySession(session);
        SpringContext.getSkillService().printSkillSlot(account);
    }

    /**
     * 移除技能栏某个技能
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void removeSkillFromSlote(TSession session, CM_RemoveSkillFromSlot req) {
        Account account = SessionUtil.getAccountBySession(session);
        int index = req.getIndex() - 1;
        SpringContext.getSkillService().removeSkillFromSlot(account, index);
    }

    /**
     * 设置技能至技能栏中
     *
     * @param session
     * @param req
     */
    @HandlerAnno
    public void setSkillToSlot(TSession session, CM_SetSkillSlot req) {
        Account account = SessionUtil.getAccountBySession(session);
        int index = req.getIndex() - 1;
        SpringContext.getSkillService().setSkillToSlot(account, req.getSkillId(), index);
    }
}
