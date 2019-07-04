package com.server.publicsystem.guild.service;

import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/7/4 17:04
 */
@Component
public class GuildService {


    /**
     * 创建工会
     *
     * @param account
     * @param guildName
     */
    public void createGuild(Account account, String guildName) {

    }

    /**
     * 退出工会
     *
     * @param account
     */
    public void exitGuild(Account account) {

    }

    /**
     * 申请加入工会
     *
     * @param account
     * @param guildId
     */
    public void applyGuild(Account account, long guildId) {

    }

    /**
     * 改变行会会员职位
     *
     * @param account
     * @param accountId
     * @param type
     */
    public void changeGuildPosition(Account account, String accountId, int type) {

    }

    /**
     * 显示所有行会列表
     *
     * @param account
     */
    public void printGuildList(Account account) {

    }

    /**
     * 显示指定工会信息
     *
     * @param account
     * @param guildId
     */
    public void printGuildInfo(Account account, long guildId) {

    }
}
