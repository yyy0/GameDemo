package com.client.clientframe.frame;

import com.SpringContext;
import com.client.clientframe.panel.ModelPanel;
import com.client.dispatcher.ClientHandlerAnno;
import com.server.common.resource.ResourceManager;
import com.server.gm.packet.CM_GMcommand;
import com.server.gm.service.GmDefinition;
import com.server.map.packet.*;
import com.server.monster.model.Monster;
import com.server.publicsystem.guild.constant.GuildPositionType;
import com.server.publicsystem.guild.packet.SM_GuildApplyList;
import com.server.publicsystem.guild.packet.SM_GuildInfo;
import com.server.publicsystem.guild.packet.SM_GuildList;
import com.server.publicsystem.guild.packet.bean.GuildVO;
import com.server.publicsystem.i18n.packet.SM_Notify_Message;
import com.server.publicsystem.i18n.resource.I18NResource;
import com.server.rank.model.FightPowerInfoVO;
import com.server.rank.packet.SM_FightPowerRank;
import com.server.server.message.MessageContent;
import com.server.task.model.TaskVO;
import com.server.task.packet.SM_TaskFinish;
import com.server.task.packet.SM_TaskInfo;
import com.server.user.account.packet.SM_AccountInfo;
import com.server.user.account.packet.SM_FightAccountInfo;
import com.server.user.attribute.constant.AttributeType;
import com.server.user.attribute.constant.GlobalConstant;
import com.server.user.attribute.model.Attribute;
import com.server.user.equipment.constant.EquipmentPosition;
import com.server.user.equipment.model.Equipment;
import com.server.user.equipment.packet.SM_EquipsInfo;
import com.server.user.fight.packet.SM_Attacker;
import com.server.user.fight.packet.SM_Buff;
import com.server.user.fight.packet.SM_Hit;
import com.server.user.item.model.AbstractItem;
import com.server.user.item.packet.SM_BagInfo;
import com.server.user.item.packet.SM_WareInfo;
import com.server.user.skill.packet.SM_AllSkill;
import com.server.user.skill.packet.SM_SlotSkill;
import com.server.user.skill.resource.SkillResource;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.*;

/**
 * 游戏指令界面
 *
 * @author yuxianming
 * @date 2019/5/20 15:43
 */
@Component
public class CommandFrame extends JFrame {

    private static final String APPLICATION_CONTEXT = "applicationContext.xml";
    private static ClassPathXmlApplicationContext applicationContext;
    private Channel channel;
    @Autowired
    private ResourceManager resourceManager;

    public static Logger logger = LoggerFactory.getLogger(CommandFrame.class);

    Container container = this.getContentPane();

    /**
     * 功能模块指令下拉框
     */
    JComboBox modelBox = new JComboBox();

    /**
     * 指令发送按钮
     */
    JButton sendButton = new JButton("发送");

    /**
     * 指令发送输入框
     */
    JTextField commandField = new JTextField();

    JLabel printLabel = new JLabel("输出游戏信息");

    JButton clearAreaButton = new JButton("清空信息");

    /**
     * 回显信息输出框
     */
    JTextArea printArea = new JTextArea();

    /**
     * 回显信息滑动框
     */
    JScrollPane scrollPane = new JScrollPane();

    JLabel accountLabel = new JLabel();

    public void initFrame(Channel channel, String accountId) {
        this.setTitle("游戏指令界面");
        this.channel = channel;
        container.setLayout(null);
        printArea.setLineWrap(true);

        //功能模块下拉框
        modelBox.addItem("全部指令");
        Map<String, Map<String, GmDefinition>> gmModelDefinitions = SpringContext.getGmDispatcher().getGmModelDefinitions();
        for (String keyName : gmModelDefinitions.keySet()) {
            modelBox.addItem(keyName);
        }

        /** 功能panel */
        JScrollPane modelPanel = new ModelPanel(channel);

        accountLabel.setText("角色: " + accountId);
        accountLabel.setBounds(20, 10, 100, 50);

//        modelBox.setBounds(20, 10, 150, 50);
        modelPanel.setBounds(20, 70, 320, 330);
        commandField.setBounds(20, 410, 210, 40);
        sendButton.setBounds(250, 410, 70, 40);
        printLabel.setBounds(350, 20, 80, 20);
        scrollPane.setBounds(350, 70, 350, 400);
        printArea.setBounds(350, 70, 350, 400);
        clearAreaButton.setBounds(470, 20, 100, 40);

        //添加button监听器
        addListener();

        // TODO  modelBox将在后续完成 显示指定gm功能模块
//        container.add(modelBox);
        container.add(accountLabel);
        container.add(modelPanel);
        container.add(commandField);
        container.add(sendButton);
        container.add(printLabel);
        container.add(scrollPane);
        scrollPane.setViewportView(printArea);
        container.add(clearAreaButton);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 500);
        this.setLocation(450, 150);
    }


    public static void main(String[] args) {

        applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT);
        applicationContext.start();
        new CommandFrame();
    }

    public void addListener() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commandField.getText();
                if (command == "") {
                    return;
                }
                CM_GMcommand packet = CM_GMcommand.vauleOf(command);
                MessageContent message = new MessageContent(packet);
                channel.writeAndFlush(message);
            }
        });

        clearAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printArea.setText("");
            }
        });
    }

    /**
     * 打印账号信息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void clientPrintAccount(SM_AccountInfo packet) {
        printArea.setText("");
        System.out.println("打印账号信息");
        String message = packet.toString();
        printArea.append(message + "\r\n");
        printArea.append("账号属性信息如下：" + "\r\n");
        Map<String, String> attributes = packet.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {

            printArea.append("name：" + entry.getKey() + " value:" + entry.getValue() + "\r\n");
        }

    }

    /**
     * 打印战斗账号信息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void clientPrintFightAccount(SM_FightAccountInfo packet) {
        printArea.setText("");
        System.out.println("打印战斗账号信息");
        String message = packet.toString();
        printArea.append(message + "\r\n");
        printArea.append("战斗账号属性如下：" + "\r\n");
        Map<String, String> attributes = packet.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            printArea.append("name：" + entry.getKey() + " value:" + entry.getValue() + "\r\n");
        }

    }

    @ClientHandlerAnno
    public void clientChangeMap(SM_ChangeMap packet) {

        String message = packet.toString();
        printArea.append(message + "\r\n");
    }

    @ClientHandlerAnno
    public void clientMove(SM_AccountMove packet) {
        String message = packet.toString();
        printArea.append(message + "\r\n");
    }

    /**
     * 打印地图信息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printMapInfo(SM_MapInfo packet) {

        long countDown = packet.getCoutDown();
        if (countDown != 0) {
            printArea.append("当前地图剩余时间：" + countDown / 1000 + " 秒\r\n");
        }
        char[][] mapInfo = packet.getMapGrids();
        for (int i = 0; i < mapInfo.length; i++) {
            for (int j = 0; j < mapInfo[i].length; j++) {
                printArea.append(String.valueOf(mapInfo[i][j]));
            }
            printArea.append("\r\n");
        }
    }

    /**
     * 打印背包消息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printBagInfo(SM_BagInfo packet) {
        printArea.setText("");
        AbstractItem[] items = packet.getItemData();

        printItems(items);
    }

    /**
     * 打印仓库信息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printWarehouseInfo(SM_WareInfo packet) {
        printArea.setText("");
        AbstractItem[] items = packet.getItemData();
        printItems(items);
    }

    private void printItems(AbstractItem[] items) {
        for (AbstractItem item : items) {
            if (item == null) {
                continue;
            }
            printArea.append(item.toString() + "\r\n");
        }
    }

    /**
     * 打印提示消息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printNotifyMessage(SM_Notify_Message packet) {
        int id = packet.getI18Id();
        Map<Integer, Object> i18Resources = resourceManager.getResources(I18NResource.class.getSimpleName());
        I18NResource resource = (I18NResource) i18Resources.get(id);
        if (resource == null) {
            logger.error("找不到对应配置id：{}" + id);
            printArea.append("找不到对应提示信息id：" + id);
            return;
        }
        String message = resource.getValue();
        printArea.append(message + "\r\n");
    }


    /**
     * 打印装备信息
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printEquips(SM_EquipsInfo packet) {
        Map<Integer, Equipment> equipmentMap = packet.getEquipmentMap();

        for (Map.Entry<Integer, Equipment> entry : equipmentMap.entrySet()) {
            EquipmentPosition position = EquipmentPosition.typeOf(entry.getKey());
            printArea.append(position + ":" + entry.getValue() + "\r\n");
            if (entry.getValue() != null) {
                Map<AttributeType, Attribute> attributes = entry.getValue().getAttributeMap();
                DecimalFormat df = new DecimalFormat("0.00%");
                for (Attribute attribute : attributes.values()) {
                    String value;

                    if (attribute.isRateAttribute()) {
                        value = df.format(GlobalConstant.getRatio(attribute.getValue()));
                    } else {
                        value = String.valueOf(attribute.getValue());
                    }
                    printArea.append("name：" + attribute.getName() + " value:" + value + "\r\n");
                }
            }
        }

    }

    @ClientHandlerAnno
    public void printMonsters(SM_MonsterInfo packet) {
        printArea.setText("");
        List<Monster> monsters = packet.getMonsters();
        if (monsters == null) {
            printArea.append("当前地图【" + packet.getMapName() + "】没有怪物" + "\r\n");
            return;
        }
        int num = monsters.size();
        printArea.append("当前地图【" + packet.getMapName() + "】怪物数量：" + num + "只" + "\r\n");
        for (Monster monster : monsters) {
            printArea.append(monster.toString() + "\r\n");
        }
    }


    @ClientHandlerAnno
    public void printAllSkill(SM_AllSkill packet) {
        printArea.setText("");
        Set<Integer> skills = packet.getAllSkill();
        for (int skillId : skills) {
            SkillResource resource = SpringContext.getSkillService().getSkillResource(skillId);
            printArea.append("技能id：" + resource.getId() + " 技能名称：" + resource.getName() + "\r\n");
        }
    }

    @ClientHandlerAnno
    public void printSlotSkill(SM_SlotSkill packet) {
        printArea.setText("");
        int[] skills = packet.getSkills();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] == 0) {
                printArea.append("第" + (i + 1) + "个槽位：" + "暂无技能" + "\r\n");
                continue;
            }
            SkillResource resource = SpringContext.getSkillService().getSkillResource(skills[i]);
            printArea.append("第" + (i + 1) + "个槽位：" + resource.getName() + "\r\n");
        }
    }

    /**
     * 受击协议
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printHit(SM_Hit packet) {
        printArea.append("您被【" + packet.getAccountId() + "】使用【" + packet.getSkillName() + "】攻击了,受到伤害:"
                + " " + packet.getDamage()
                + " 血量： " + packet.getCurHP() + "/" + packet.getMax_HP() + "\r\n"
        );
    }

    /**
     * 攻击者信息 您使用xx技能攻击了xx，造成伤害: xx
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void printAttacker(SM_Attacker packet) {
        printArea.append("您使用了【" + packet.getSkillName() + "】攻击" + "【" + packet.getVictim() + "】,造成伤害："
                + packet.getDamage() + "\r\n");
    }

    @ClientHandlerAnno
    public void printBuff(SM_Buff packet) {
        if (packet.getDamage() > 0) {
            printArea.append("您受到【" + packet.getBuffName() + "】buff影响，扣除伤害： " + packet.getDamage() +
                    " 当前生命： " + packet.getCurHP() + "/" + packet.getMax_HP() + "\r\n");
        }
    }

    @ClientHandlerAnno
    public void printRank(SM_FightPowerRank packet) {
        List<FightPowerInfoVO> list = packet.getRank();
        if (list.size() > 0) {
            int index = 0;
            for (FightPowerInfoVO infoVO : list) {
                index++;
                printArea.append("第" + index + "名 " + "账号：" + infoVO.getAccountId() + "  战力： " + infoVO.getFightPower() + "\r\n");
            }
        } else {
            printArea.append("排行榜暂无数据" + "\r\n");
        }
    }

    /**
     * 通知任务领奖
     *
     * @param packet
     */
    @ClientHandlerAnno
    public void notifyRewardTask(SM_TaskFinish packet) {
        printArea.append("任务[" + packet.getTaskId() + "]已完成，快去领奖吧！" + "\r\n");
    }

    @ClientHandlerAnno
    public void printTaskInfo(SM_TaskInfo packet) {
        List<TaskVO> list = packet.getList();
        if (list.isEmpty()) {
            printArea.append("您当前没有进行中的任务！" + "\r\n");
            return;
        } else {
            for (TaskVO taskVO : list) {
                printArea.append("任务[" + taskVO.getTaskId() + "] 当前进度： " + taskVO.getProgress() + "/" + taskVO.getFinalValue() + "\r\n");
            }
        }
    }

    @ClientHandlerAnno
    public void notifyLeaveMap(final SM_NotifyLeaveMap packet) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            long countDown = packet.getCountDown();

            @Override
            public void run() {

                printArea.append("您即将退出地图：" + packet.getLeaveMapId() + "  剩余时间：" + countDown / 1000 + "秒\r\n");
                countDown -= 1000;
                if (countDown <= 0) {
                    System.gc();
                    this.cancel();
                }
            }
        }, 0, 1000);

    }

    @ClientHandlerAnno
    public void printGuildInfo(SM_GuildInfo packet) {
        long guildId = packet.getGuildId();
        String name = packet.getGuildName();
        printArea.append("行会id:" + guildId + "   行会名称：" + name + "\r\n" + "成员信息如下：" + "\r\n");
        Map<String, GuildPositionType> members = packet.getMembers();
        for (Map.Entry<String, GuildPositionType> entry : members.entrySet()) {
            printArea.append("成员id：" + entry.getKey() + "  职位： " + entry.getValue().getPosition() + "\r\n");
        }
    }

    @ClientHandlerAnno
    public void printGuildList(SM_GuildList packet) {
        List<GuildVO> list = packet.getVoList();
        if (list == null || list.size() == 0) {
            printArea.append("暂时没有任何工会" + "\r\n");
            return;
        }
        for (GuildVO vo : list) {
            printArea.append("工会id:" + vo.getGuildId() + "  名称:" + vo.getName() + "  会长:" + vo.getLeader() + "  会员数:" + vo.getMemberNum() + "\r\n");
        }
    }

    @ClientHandlerAnno
    public void printGuildApply(SM_GuildApplyList packet) {
        Set<String> members = packet.getMembers();
        if (members == null || members.size() == 0) {
            printArea.append("暂时没有申请信息" + "\r\n");
        }
        printArea.append("申请列表如下: \r\n");
        for (String member : members) {
            printArea.append(member + "\r\n");
        }
    }

}
