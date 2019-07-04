package com;

import com.client.clientframe.frame.CommandFrame;
import com.client.clientframe.frame.LoginFrame;
import com.client.clientframe.frame.RegFrame;
import com.server.challengeMap.ChallengeMapService;
import com.server.common.event.core.EventBusManager;
import com.server.common.executor.account.AccountExecutorService;
import com.server.common.executor.scene.SceneExecutorService;
import com.server.common.executor.schedule.ScheduleService;
import com.server.common.identity.service.IdentifyService;
import com.server.common.resource.ResourceManager;
import com.server.common.service.GlobalService;
import com.server.dispatcher.ActionDispatcher;
import com.server.gm.service.Command;
import com.server.gm.service.CommandFacade;
import com.server.gm.service.GmDispatcher;
import com.server.login.service.LoginService;
import com.server.map.service.MapManager;
import com.server.map.service.WorldService;
import com.server.publicsystem.guild.service.GuildService;
import com.server.rank.service.RankService;
import com.server.session.service.SessionService;
import com.server.task.service.TaskService;
import com.server.user.account.service.AccountService;
import com.server.user.attribute.service.AttributeManager;
import com.server.user.buff.service.BuffService;
import com.server.user.equipUpgrade.service.EquipUpgradeService;
import com.server.user.equipment.service.EquipmentService;
import com.server.user.fight.service.FightService;
import com.server.user.item.service.StoreService;
import com.server.user.skill.service.SkillService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yuxianming
 * @date 2019/4/25 15:18
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static SpringContext instance;
    public ApplicationContext applicationContext;

    @Autowired
    public Command command;

    @Autowired
    public EventBusManager eventBusManager;

    @Autowired
    public ActionDispatcher actionDispatcher;

    @Autowired
    public CommandFacade commandFacade;

    @Autowired
    public WorldService worldService;

    @Autowired
    public AccountService accountService;

    @Autowired
    public LoginService loginService;

    @Autowired
    public GlobalService globalService;

    @Autowired
    public StoreService storeService;

    @Autowired
    public SessionService sessionService;

    @Autowired
    public IdentifyService identifyService;

    @Autowired
    public GmDispatcher gmDispatcher;

    @Autowired
    public LoginFrame loginFrame;

    @Autowired
    public RegFrame regFrame;

    @Autowired
    public CommandFrame commandFrame;

    @Autowired
    public EquipmentService equipmentService;

    @Autowired
    public AttributeManager attributeManager;

    @Autowired
    public ResourceManager resourceManager;

    @Autowired
    public EquipUpgradeService equipUpgradeService;

    @Autowired
    public AccountExecutorService accountExecutorService;

    @Autowired
    public SkillService skillService;

    @Autowired
    public ScheduleService scheduleService;

    @Autowired
    public FightService fightService;

    @Autowired
    public MapManager mapManager;

    @Autowired
    public SceneExecutorService sceneExecutorService;

    @Autowired
    public BuffService buffService;

    @Autowired
    public RankService rankService;

    @Autowired
    public TaskService taskService;

    @Autowired
    public ChallengeMapService challengeMapService;

    @Autowired
    public GuildService guildService;

    public static GuildService getGuildService() {
        return instance.guildService;
    }

    public static ChallengeMapService getChallengeMapService() {
        return instance.challengeMapService;
    }


    public static TaskService getTaskService() {
        return instance.taskService;
    }

    public static RankService getRankService() {
        return instance.rankService;
    }

    public static BuffService getBuffService() {
        return instance.buffService;
    }

    public static SceneExecutorService getSceneExecutorService() {
        return instance.sceneExecutorService;
    }

    public static FightService getFightService() {
        return instance.fightService;
    }

    public static MapManager getMapManager() {
        return instance.mapManager;
    }

    public static ScheduleService getScheduleService() {
        return instance.scheduleService;
    }

    public static SkillService getSkillService() {
        return instance.skillService;
    }

    public static AccountExecutorService getAccountExecutorService() {
        return instance.accountExecutorService;
    }

    public static EquipUpgradeService getEquipUpgradeService() {
        return instance.equipUpgradeService;
    }

    public static ResourceManager getResourceManager() {
        return instance.resourceManager;
    }

    public static AttributeManager getAttributeManager() {
        return instance.attributeManager;
    }

    public static LoginFrame getLoginFrame() {
        return instance.loginFrame;
    }

    public static RegFrame getRegFrame() {
        return instance.regFrame;
    }

    public static CommandFrame getCommandFrame() {
        return instance.commandFrame;
    }

    public static GmDispatcher getGmDispatcher() {
        return instance.gmDispatcher;
    }


    public static EventBusManager getEventManager() {
        return instance.eventBusManager;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    @PostConstruct
    private final void init() {
        instance = this;
    }


    public static CommandFacade getCommandFacade() {
        return instance.commandFacade;
    }

    public static SpringContext getInstance() {
        return instance;
    }

    public ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    public static Command getCommand() {
        return instance.command;
    }

    public static WorldService getWorldService() {
        return instance.worldService;
    }

    public static AccountService getAccountService() {
        return instance.accountService;
    }

    public static LoginService getLoginService() {
        return instance.loginService;
    }

    public static GlobalService getGlobalService() {
        return instance.globalService;
    }

    public static ActionDispatcher getActionDispatcher() {
        return instance.actionDispatcher;
    }


    public static StoreService getStoreService() {
        return instance.storeService;
    }

    public static SessionService getSessionService() {
        return instance.sessionService;
    }

    public static IdentifyService getIdentifyService() {
        return instance.identifyService;
    }

    public static EquipmentService getEquipmentService() {
        return instance.equipmentService;
    }


}
