package com.server.rank.model;

import com.server.tool.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战力排行榜
 *
 * @author yuxianming
 * @date 2019/6/22 17:14
 */
@Component
public class FightPowerRank {

    private CopyOnWriteArrayList<FightPowerInfo> fightPowerList = new CopyOnWriteArrayList<>();


    public int getIndex(FightPowerInfo info) {
        return fightPowerList.indexOf(info);
    }

    public void updateRank(FightPowerInfo info) {

        boolean isAdd = true;
        for (FightPowerInfo temp : fightPowerList) {
            if (info.getAccountId().equals(temp.getAccountId())) {
                isAdd = false;
                temp.setFightPower(info.getFightPower());
                temp.setRefreshTime(TimeUtil.now());
                break;
            }
        }
        if (isAdd) {
            fightPowerList.add(info);
        }
        Collections.sort(fightPowerList);
    }

    public void sortRank() {
        Collections.sort(fightPowerList);
    }

    public CopyOnWriteArrayList<FightPowerInfo> getFightPowerList() {
        return fightPowerList;
    }

    public void setFightPowerList(CopyOnWriteArrayList<FightPowerInfo> fightPowerList) {
        this.fightPowerList = fightPowerList;
    }

    public void addInfo(FightPowerInfo info) {
        fightPowerList.add(info);
    }
}
