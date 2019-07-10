package com.server.user.account.model;

import com.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.map.model.Grid;
import com.server.publicsystem.guild.constant.GuildPositionType;
import com.server.user.account.resource.AccountResource;
import com.server.user.attribute.AttributeUtil;
import com.server.user.attribute.constant.AttributeModel;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.model.AccountAttribute;
import com.server.user.attribute.model.Attribute;
import com.server.user.equipment.model.EquipStorage;
import com.server.user.fight.FightAccount;
import com.server.user.fight.command.FightSyncCommand;
import com.server.user.fight.syncStrategy.AbstractAccountSyncStrategy;
import com.server.user.skill.model.AccountSkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;


/**
 * @author yuxianming
 * @date 2019/4/25 17:35
 */

public class Account {

    public static Logger logger = LoggerFactory.getLogger(Account.class);

    private String accountId;

    private String name;

    private String pwd;

    private Date createTime;

    /**
     * 当前地图id  也是上次所在地图
     */
    private int mapId;

    /**
     * 上次进入地图
     */
    private int oldMapId;

    /**
     * 当前地图坐标（也是上次地图坐标）
     */
    private int gridX;

    private int girdY;

    private int level;

    private long fightPower;

    @JsonIgnore
    private transient AccountAttribute attribute;

    @JsonIgnore
    private transient FightAccount fightAccount;

    /**
     * 工会id
     */
    private long guildId;

    /**
     * 工会职位
     */
    private GuildPositionType guildPosition;

    private String guildName;



    /**
     * 初始化账号信息
     *
     * @param accountId
     * @param name
     * @param pwd
     * @return
     */
    public static Account valueOf(String accountId, String name, String pwd) {
        Account info = new Account();
        info.accountId = accountId;
        info.name = name;
        info.pwd = pwd;
        info.mapId = 1;
        info.gridX = 1;
        info.girdY = 1;
        info.createTime = new Date();
        info.level = 1;
        AccountResource resource = SpringContext.getAccountService().getAccountResource(info.level);
        //人物基础属性
        SpringContext.getAttributeManager().putAttributes(accountId, AttributeModel.ACCOUNT_BASE, resource.getAttributes());
        return info;
    }

    public static Account valueOf(String account) {
        Account info = new Account();
        return info;
    }


    public AccountAttribute getAccountAttribute() {
        return SpringContext.getAttributeManager().getAccountAttribute(accountId);
    }

    @JsonIgnore
    public Map<AttributeType, Attribute> getCurCopyAttribute() {
        Map<AttributeType, Attribute> result = AttributeUtil.copy(getAccountAttribute().getFinalAttribute());
        return result;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGirdY() {
        return girdY;
    }

    public void setGirdY(int girdY) {
        this.girdY = girdY;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttribute(AccountAttribute attribute) {
        this.attribute = attribute;
    }

    public int getOldMapId() {
        return oldMapId;
    }

    public void setOldMapId(int oldMapId) {
        this.oldMapId = oldMapId;
    }

    @Override
    public String toString() {

        AccountAttribute accountAttribute = getAccountAttribute();
        return "账号信息：Account{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", level=" + level +
                ", mapId=" + mapId +
                ", gridX=" + gridX +
                ", girdY=" + girdY +
                '}' +
                "\r\n" + accountAttribute;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Grid getGrid() {
        return Grid.valueOf(this.gridX, this.girdY);
    }

    public AccountSkill getAccountSkill() {
        return SpringContext.getSkillService().getAccountSkill(accountId);
    }

    public FightAccount getFightAccount() {

        if (fightAccount == null) {
            return FightAccount.valueOf(this);
        }
        return fightAccount;
    }

    public void setFightAccount(FightAccount fightAccount) {
        this.fightAccount = fightAccount;
    }


    /**
     * 同步至战斗账号
     *
     * @param strategy
     */
    public void fightSync(AbstractAccountSyncStrategy strategy) {

        strategy.init(this);
        FightAccount fightAccount = getFightAccount();

        //账号线程同步
        strategy.syncInfo(fightAccount);

        //场景线程同步
        SpringContext.getSceneExecutorService().submit(FightSyncCommand.valueOf(strategy, this.accountId, this.mapId));

    }

    public long getFightPower() {
        return fightPower;
    }

    public void setFightPower(long fightPower) {
        this.fightPower = fightPower;
    }

    public EquipStorage getEquipStorage() {
        EquipStorage equipStorage = SpringContext.getEquipmentService().getEquipStorage(accountId);
        return equipStorage;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public GuildPositionType getGuildPosition() {
        return guildPosition;
    }

    public void setGuildPosition(GuildPositionType guildPosition) {
        this.guildPosition = guildPosition;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void exitGuild() {
        this.setGuildPosition(null);
        this.setGuildName(null);
        this.setGuildId(0);
    }

    public void joinGuild(long guildId, String name) {
        this.setGuildId(guildId);
        this.setGuildName(name);
        this.setGuildPosition(GuildPositionType.MEMBER);
    }
}
