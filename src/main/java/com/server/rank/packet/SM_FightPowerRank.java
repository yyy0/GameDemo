package com.server.rank.packet;

import com.server.rank.model.FightPowerInfo;
import com.server.rank.model.FightPowerInfoVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/6/24 18:16
 */
public class SM_FightPowerRank implements Serializable {

    private List<FightPowerInfoVO> rank;

    public static SM_FightPowerRank valueOf(List<FightPowerInfo> list) {
        SM_FightPowerRank packet = new SM_FightPowerRank();
        packet.rank = new ArrayList<>(list.size());
        for (FightPowerInfo info : list) {
            packet.rank.add(FightPowerInfoVO.valueOf(info));
        }
        return packet;
    }

    public List<FightPowerInfoVO> getRank() {
        return rank;
    }

    public void setRank(List<FightPowerInfoVO> rank) {
        this.rank = rank;
    }
}
