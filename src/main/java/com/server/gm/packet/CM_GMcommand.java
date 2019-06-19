package com.server.gm.packet;

import java.io.Serializable;

/**
 * @author yuxianming
 * @date 2019/5/9 16:01
 */
public class CM_GMcommand implements Serializable {
    private String command;

    public static CM_GMcommand vauleOf(String command) {
        CM_GMcommand cm = new CM_GMcommand();
        cm.command = command;
        return cm;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
