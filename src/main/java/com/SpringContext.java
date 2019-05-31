package com;

import com.client.clientframe.frame.CommandFrame;
import com.client.clientframe.frame.LoginFrame;
import com.client.clientframe.frame.RegFrame;
import com.server.command.service.Command;
import com.server.command.service.CommandFacade;
import com.server.command.service.GmDispatcher;
import com.server.common.event.core.EventBusManager;
import com.server.common.identity.service.IdentifyService;
import com.server.common.service.GlobalService;
import com.server.dispatcher.ActionDispatcher;
import com.server.login.service.LoginService;
import com.server.map.service.WorldService;
import com.server.session.service.SessionService;
import com.server.user.account.service.AccountService;
import com.server.user.equipment.service.EquipmentService;
import com.server.user.item.service.ItemService;
import com.server.user.item.service.StoreService;
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
    public ItemService itemService;

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

    public static ItemService getItemService() {
        return instance.itemService;
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
