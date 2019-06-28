package com.server.rank.gm;

import com.SpringContext;
import com.server.gm.anno.GmAnno;
import com.server.gm.anno.GmMethod;
import com.server.rank.packet.CM_RankInfo;
import com.server.user.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/24 14:26
 */
@GmAnno(title = "排行榜gm")
@Component
public class RankGm {

    @GmMethod(name = "打印排行榜", clz = CM_RankInfo.class)
    public void printRank(Account account) {
        SpringContext.getRankService().printRankInfo(account);
    }
}
