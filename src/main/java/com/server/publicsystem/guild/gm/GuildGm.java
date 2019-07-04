package com.server.publicsystem.guild.gm;

import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.publicsystem.guild.packet.*;
import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/7/4 11:13
 */
@GmAnno(title = "工会gm")
public class GuildGm {

    @GmMethod(name = "创建工会", param = "参数：工会名称", clz = CM_CreateGuild.class)
    public void createGuild(Account account, String guildName) {
    }

    @GmMethod(name = "改工会职位", param = "参数:账号id 工会职位", clz = CM_ChangeGuildPosition.class)
    public void changeGuildPosition(Account account, String accountId, int type) {
    }

    @GmMethod(name = "加入工会", param = "参数:工会id", clz = CM_ApplyGuild.class)
    public void applyGuild(Account account, long guildId) {
    }

    @GmMethod(name = "退出工会", clz = CM_ExitGuild.class)
    public void exitGuild(Account account) {
    }

    @GmMethod(name = "工会信息", param = "参数：工会id", clz = CM_GuildInfo.class)
    public void printGuildInfo(Account account, long guildId) {
    }

    @GmMethod(name = "工会列表", clz = CM_GuildList.class)
    public void printGuildList(Account account) {

    }
}