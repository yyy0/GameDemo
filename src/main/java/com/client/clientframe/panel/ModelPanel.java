package com.client.clientframe.panel;

import com.SpringContext;
import com.client.clientframe.constant.ClientFrameConstant;
import com.server.command.service.GmDefinition;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author yuxianming
 * @date 2019/5/20 17:03
 */
public class ModelPanel extends JScrollPane {

    private JPanel allPanel;
    private Channel channel;

    public ModelPanel(Channel channel) {


        this.channel = channel;
        //gm模块面板高度
        int height = SpringContext.getGmDispatcher().getModelPanelHeight();

        allPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Map<String, Map<String, GmDefinition>> gmModelDefinitions = SpringContext.getGmDispatcher().getGmModelDefinitions();
        allPanel.setPreferredSize(new Dimension(300, ClientFrameConstant.COMMAND_PANEL_BIGHEIGHT * height));
        for (String keyName : gmModelDefinitions.keySet()) {
            allPanel.add(addModelPanel(keyName));
        }

        this.getViewport().add(allPanel);
    }

    /**
     * 创建功能模块panel
     *
     * @param modelName 功能模块名称
     * @return
     */
    public JPanel addModelPanel(String modelName) {
        JPanel modelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel label = new JLabel(modelName);
        Map<String, GmDefinition> gmDefinitions = SpringContext.getGmDispatcher().getModelDefinition(modelName);
        modelPanel.setPreferredSize(new Dimension(300, (gmDefinitions.size() + 1) * ClientFrameConstant.COMMAND_PANEL_MEDIUMHEIGHT));
        modelPanel.add(label);

        //初始化命令方法panel
        for (Map.Entry<String, GmDefinition> entry : gmDefinitions.entrySet()) {
            CommandPanel commandPanel = entry.getValue().getCommandPanel();
            commandPanel.initPanel(channel);
            modelPanel.add(commandPanel);
        }
        return modelPanel;
    }


}
