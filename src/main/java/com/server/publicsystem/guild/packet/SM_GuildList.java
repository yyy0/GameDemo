package com.server.publicsystem.guild.packet;

import com.server.publicsystem.guild.packet.bean.GuildVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/7/5 21:55
 */
public class SM_GuildList implements Serializable {

    List<GuildVO> voList;

    public static SM_GuildList valueOf(List<GuildVO> list) {
        SM_GuildList packet = new SM_GuildList();
        packet.voList = new ArrayList<>(list);
        return packet;
    }

    public List<GuildVO> getVoList() {
        return voList;
    }

    public void setVoList(List<GuildVO> voList) {
        this.voList = voList;
    }
}
