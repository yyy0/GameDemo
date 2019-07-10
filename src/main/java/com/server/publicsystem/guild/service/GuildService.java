package com.server.publicsystem.guild.service;

import com.SpringContext;
import com.server.common.entity.CommonEntManager;
import com.server.common.identity.service.IdentifyService;
import com.server.publicsystem.guild.constant.GuildPositionType;
import com.server.publicsystem.guild.constant.HandlerApplyType;
import com.server.publicsystem.guild.entity.AccountGuildEnt;
import com.server.publicsystem.guild.entity.GuildEnt;
import com.server.publicsystem.guild.model.AccountGuildLock;
import com.server.publicsystem.guild.model.GuildInfo;
import com.server.publicsystem.guild.model.GuildLock;
import com.server.publicsystem.guild.packet.SM_GuildApplyList;
import com.server.publicsystem.guild.packet.SM_GuildInfo;
import com.server.publicsystem.guild.packet.SM_GuildList;
import com.server.publicsystem.guild.packet.bean.GuildVO;
import com.server.publicsystem.i18n.I18Utils;
import com.server.publicsystem.i18n.constant.I18nId;
import com.server.tool.PacketSendUtil;
import com.server.user.account.model.Account;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

/**
 * @author yuxianming
 * @date 2019/7/4 17:04
 */
@Component
public class GuildService {

    public static Logger logger = LoggerFactory.getLogger(GuildService.class);

    @Autowired
    private CommonEntManager<Long, GuildEnt> guildEntManager;

    @Autowired
    private CommonEntManager<String, AccountGuildEnt> accountEntManager;

    /**
     * 行会锁
     */
    private ConcurrentHashMap<Long, GuildLock> guildLockMap = new ConcurrentHashMap<>();

    /**
     * 个人行会信息锁
     */
    private ConcurrentHashMap<String, AccountGuildLock> accountGuildLockMap = new ConcurrentHashMap<>();

    /**
     * key 行会id  value 行会名称
     * 用于防止名称重复
     */
    private CopyOnWriteArrayList<String> guildINameList = new CopyOnWriteArrayList<>();

    public GuildLock getGuildLock(long guildId) {
        return guildLockMap.get(guildId);
    }

    public void putGuildLock(GuildLock lock) {
        guildLockMap.putIfAbsent(lock.getGuildId(), lock);
    }

    public AccountGuildLock getOrCreateAccountGuildLock(String accountId) {
        AccountGuildLock lock = accountGuildLockMap.get(accountId);
        if (lock == null) {
            lock = new AccountGuildLock(accountId);
            AccountGuildLock temp = accountGuildLockMap.putIfAbsent(accountId, lock);
            if (temp != null) {
                lock = temp;
            }
        }
        return lock;
    }

    public void addAccountGuildLock(AccountGuildLock lock) {
        accountGuildLockMap.putIfAbsent(lock.getAccountId(), lock);
    }

    public GuildEnt getGuildEnt(long guildId) {
        return guildEntManager.getEnt(GuildEnt.class, guildId);
    }

    public GuildInfo getGuildInfo(long guildId) {
        GuildEnt ent = getGuildEnt(guildId);
        if (ent == null) {
            return null;
        }
        ent.doDeserialize();
        return ent.getGuildInfo();
    }

    public void saveGuildInfo(GuildInfo guildInfo) {
        GuildEnt ent = getGuildEnt(guildInfo.getGuildId());
        ent.doSerialize(guildInfo);
        guildEntManager.update();
    }

    public AccountGuildEnt getOrCreateAccountGuildEnt(String accountId) {
        AccountGuildEnt ent = accountEntManager.getEnt(AccountGuildEnt.class, accountId);
        if (ent == null) {
            ent = AccountGuildEnt.valueOf(accountId, 0L, 0);
            accountEntManager.createEnt(ent);
            return ent;
        }
        return ent;
    }

    public void saveAccountGuildEnt(AccountGuildEnt ent) {
        if (ent == null) {
            return;
        }
        AccountGuildEnt saveEnt = getOrCreateAccountGuildEnt(ent.getAccountId());
        saveEnt.copyEnt(ent);
        accountEntManager.update();
    }

    public void initGuild() {
        List<GuildEnt> guildEntList = guildEntManager.namedQuery("loadAllGuild", GuildEnt.class);

        for (GuildEnt ent : guildEntList) {
            ent.doDeserialize();
            guildINameList.add(ent.getName());
            guildLockMap.put(ent.getId(), new GuildLock(ent.getId()));
        }

    }

    /**
     * 创建工会
     *
     * @param account
     * @param guildName
     */
    public void createGuild(Account account, String guildName) {

        GuildEnt guildEnt = null;
        String accountId = account.getAccountId();
        Lock lock = getOrCreateAccountGuildLock(accountId);
        try {
            lock.lock();
            AccountGuildEnt accountGuildEnt = getOrCreateAccountGuildEnt(accountId);
            long guildId = accountGuildEnt.getGuildId();
            //已加入工会的玩家不可创建工会
            if (guildId > 0) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_BEEN_JOIN);
            }

            long newGuildId = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.GUILD);

            // 行会名称是否重复
            if (guildINameList.contains(guildName)) {
                I18Utils.notifyMessage(accountId, I18nId.GUILD_NAME_EXIST);
                return;
            } else {
                guildINameList.add(guildName);
            }
            guildEnt = GuildEnt.valueOf(newGuildId, guildName, account.getAccountId());
            accountGuildEnt.setGuildId(newGuildId);
            accountGuildEnt.setPosition(GuildPositionType.LEADER.getType());
            accountGuildEnt.setGuildName(guildName);

            guildEntManager.createEnt(guildEnt);
            guildLockMap.put(newGuildId, new GuildLock(newGuildId));
            saveAccountGuildEnt(accountGuildEnt);

        } finally {
            lock.unlock();
        }

        logger.info("账号[{}]创建行会，id:{},name:{}", account.getAccountId(), guildEnt.getId(), guildEnt.getName());
        SM_GuildInfo packet = SM_GuildInfo.valueOf(guildEnt.getGuildInfo());
        PacketSendUtil.send(accountId, packet);

    }


    /**
     * 退出工会
     *
     * @param account
     */
    public void exitGuild(Account account) {

        AccountGuildEnt accountGuildEnt = getOrCreateAccountGuildEnt(account.getAccountId());
        long guildId = accountGuildEnt.getGuildId();
        if (guildId <= 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        //会长不能退会
        if (accountGuildEnt.getPosition() == GuildPositionType.LEADER.getType()) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_LEADER_CAN_NOT_EXIT);
        }

        Lock lock = getGuildLock(guildId);
        if (lock == null) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }
        try {
            lock.lock();
            GuildInfo guildInfo = getGuildInfo(guildId);
            if (guildInfo == null) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
            }

            if (accountGuildEnt.getPosition() == GuildPositionType.DEPUTY.getType()) {
                guildInfo.setDeputy(null);
            }

            guildInfo.removeMember(account.getAccountId());
            accountGuildEnt.exitGuild();
            logger.info("账号[{}]退出行会，id：{}", account.getAccountId(), guildId);

            saveAccountGuildEnt(accountGuildEnt);
            saveGuildInfo(guildInfo);
            SpringContext.getAccountService().saveAccountInfo(account);

        } finally {
            lock.unlock();
        }

        I18Utils.notifyMessage(account, I18nId.GUILD_ALREADY_EXIT);
    }

    /**
     * 申请加入工会
     *
     * @param account
     * @param guildId
     */
    public void applyGuild(Account account, long guildId) {


        AccountGuildEnt accountGuildEnt = getOrCreateAccountGuildEnt(account.getAccountId());

        //有工会的不能再加入
        if (accountGuildEnt.getGuildId() > 0) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_BEEN_JOIN);
        }
        // 非法数值
        if (guildId <= 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        //行会是否存在
        if (!guildLockMap.containsKey(guildId)) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }

        Lock lock = getGuildLock(guildId);
        if (lock == null) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }

        GuildInfo guildInfo = null;
        try {
            lock.lock();
            //二次验证
            if (accountGuildEnt.getGuildId() > 0) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_BEEN_JOIN);
            }

            guildInfo = getGuildInfo(guildId);
            Set<String> applyMembers = guildInfo.getApplyMembers();
            applyMembers.add(account.getAccountId());
            saveGuildInfo(guildInfo);
            I18Utils.notifyMessage(account, I18nId.GUILD_APPLY_SUCCESS);

        } finally {
            lock.unlock();
        }

    }

    /**
     * 改变行会会员职位
     *
     * @param account   修改者
     * @param accountId 被修改者
     * @param position  职位
     */
    public void changeGuildPosition(Account account, String accountId, int position) {

        if (StringUtils.isBlank(accountId) || position < 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        AccountGuildEnt accountGuildEnt = getOrCreateAccountGuildEnt(account.getAccountId());

        if (accountGuildEnt.getGuildId() <= 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        GuildInfo guildInfo = null;
        Lock guildLock = getGuildLock(accountGuildEnt.getGuildId());
        try {
            guildLock.lock();
            long guildId = accountGuildEnt.getGuildId();
            guildInfo = getGuildInfo(guildId);
            if (guildInfo == null) {
                I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
                return;
            }

            AccountGuildEnt memberGuildEnt = getOrCreateAccountGuildEnt(accountId);
            long memberGuild = memberGuildEnt.getGuildId();
            if (memberGuild == 0) {
                I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
            }
            //是否为同一个工会
            if (memberGuild != guildId) {
                I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
            }

            int myPosition = accountGuildEnt.getPosition();
            int memberPosition = memberGuildEnt.getPosition();

            //职位type不能大于等于 被修改者  才可修改他人职位
            //自身职位type不能大于将要修改的职位
            if (myPosition >= memberPosition || myPosition > position) {
                I18Utils.notifyMessageThrow(account, I18nId.NO_PERMISSION);
            }

            //修改职位相关
            if (position == GuildPositionType.DEPUTY.getType()) {
                guildInfo.setDeputy(accountId);
            } else if (position == GuildPositionType.LEADER.getType()) {
                guildInfo.setLeader(accountId);
            }
            memberGuildEnt.setPosition(position);
            //如果是会长换职位 原会长改为普通成员
            if (position == GuildPositionType.LEADER.getType() && myPosition == position) {
                accountGuildEnt.setPosition(GuildPositionType.MEMBER.getType());
                guildInfo.addMember(account.getAccountId(), GuildPositionType.MEMBER);
            }
            guildInfo.addMember(memberGuildEnt.getAccountId(), GuildPositionType.typeOf(position));

            saveGuildInfo(guildInfo);
            saveAccountGuildEnt(memberGuildEnt);
            saveAccountGuildEnt(accountGuildEnt);

        } finally {
            guildLock.unlock();
        }

        SM_GuildInfo packet = SM_GuildInfo.valueOf(guildInfo);
        PacketSendUtil.send(account, packet);
    }


    /**
     * 显示所有行会列表
     *
     * @param account
     */
    public void printGuildList(Account account) {

        List<GuildEnt> guildEntList = guildEntManager.namedQuery("loadAllGuild", GuildEnt.class);
        if (guildEntList == null || guildEntList.size() == 0) {
            I18Utils.notifyMessage(account, I18nId.GUILD_NULL);
            return;
        }
        List<GuildVO> voList = new ArrayList<>(guildEntList.size());
        for (GuildEnt ent : guildEntList) {
            ent.doDeserialize();
            GuildVO vo = GuildVO.valueOf(ent.getGuildInfo());
            voList.add(vo);
        }

        SM_GuildList packet = SM_GuildList.valueOf(voList);
        PacketSendUtil.send(account, packet);
    }

    /**
     * 显示指定工会信息
     *
     * @param account
     * @param guildId
     */
    public void printGuildInfo(Account account, long guildId) {
        // 非法数值
        if (guildId <= 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        //行会是否存在
        if (!guildLockMap.containsKey(guildId)) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }

        Lock lock = getGuildLock(guildId);
        if (lock == null) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }

        GuildInfo guildInfo = null;
        try {
            lock.lock();
            guildInfo = getGuildInfo(guildId);
            if (guildInfo == null) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
            }
        } finally {
            lock.unlock();
        }
        SM_GuildInfo packet = SM_GuildInfo.valueOf(guildInfo);
        PacketSendUtil.send(account, packet);

    }

    /**
     * 处理申请
     */
    public void handlerApply(Account account, String accountId, int type) {
        if (StringUtils.isBlank(accountId)) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        if (!ArrayUtils.contains(HandlerApplyType.ALL_TYPE, type)) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        //自身行会信息
        AccountGuildEnt accountGuildEnt = getOrCreateAccountGuildEnt(account.getAccountId());
        if (accountGuildEnt == null) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        //只有会长、副会长才可处理申请
        if (accountGuildEnt.getPosition() > GuildPositionType.DEPUTY.getType()) {
            I18Utils.notifyMessageThrow(account, I18nId.NO_PERMISSION);
        }
        long guildId = accountGuildEnt.getGuildId();
        GuildInfo guildInfo = getGuildInfo(guildId);
        if (guildInfo == null) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }

        //目标玩家行会信息
        AccountGuildEnt targetAccountEnt = getOrCreateAccountGuildEnt(accountId);
        long targetGuildId = targetAccountEnt.getGuildId();
        if (targetGuildId > 0) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_ACCOUNT_ALREADY_EXIST);
        }

        GuildLock guildLock = getGuildLock(guildId);
        AccountGuildLock targetAccountLock = getOrCreateAccountGuildLock(accountId);
        try {
            guildLock.lock();
            //二次验证
            if (guildInfo == null) {
                I18Utils.notifyMessage(accountId, I18nId.GUILD_NOT_EXIST);
            }
            if (accountGuildEnt.getGuildId() != guildId) {
                I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
            }

            targetAccountLock.lock();
            if (targetAccountEnt.getGuildId() != targetGuildId) {
                I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
            }

            Set<String> applyMembers = guildInfo.getApplyMembers();
            Map<String, GuildPositionType> members = guildInfo.getMembers();
            if (!applyMembers.contains(accountId)) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_ACCOUNT_NO_APPLY);
            }

            //申请列表移除该玩家
            applyMembers.remove(accountId);
            if (members.containsKey(accountId)) {
                I18Utils.notifyMessageThrow(account, I18nId.GUILD_ACCOUNT_ALREADY_EXIST);
                return;
            }

            if (type == HandlerApplyType.REFUSE) {
                return;
            } else if (type == HandlerApplyType.AGREE) {
                guildInfo.addMember(accountId, GuildPositionType.MEMBER);
                targetAccountEnt.setGuildId(guildId);
                targetAccountEnt.setPosition(GuildPositionType.MEMBER.getType());
                targetAccountEnt.setGuildName(guildInfo.getName());

            }
            saveGuildInfo(guildInfo);
            saveAccountGuildEnt(targetAccountEnt);
        } finally {
            targetAccountLock.unlock();
            guildLock.unlock();
        }
        SM_GuildInfo packet = SM_GuildInfo.valueOf(guildInfo);
        PacketSendUtil.send(account, packet);
    }

    public void printApplyList(Account account, long guildId) {

        if (guildId <= 0) {
            I18Utils.notifyMessageThrow(account, I18nId.ILLEGAL_VALUE);
        }
        GuildInfo guildInfo = getGuildInfo(guildId);
        if (guildInfo == null) {
            I18Utils.notifyMessageThrow(account, I18nId.GUILD_NOT_EXIST);
        }

        Lock lock = getGuildLock(guildId);
        try {
            lock.lock();
            Set<String> members = guildInfo.getApplyMembers();
            if (CollectionUtils.isEmpty(members)) {
                I18Utils.notifyMessage(account, I18nId.GUILD_NO_APPLY);
                return;
            }
            SM_GuildApplyList packet = SM_GuildApplyList.valueOf(members);
            PacketSendUtil.send(account, packet);

        } finally {
            lock.unlock();
        }
    }
}
