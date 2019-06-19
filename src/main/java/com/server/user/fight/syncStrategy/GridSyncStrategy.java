package com.server.user.fight.syncStrategy;

import com.server.map.model.Grid;
import com.server.user.account.model.Account;
import com.server.user.fight.FightAccount;

/**
 * 坐标同步
 *
 * @author yuxianming
 * @date 2019/6/14 16:10
 */
public class GridSyncStrategy extends AbstractAccountSyncStrategy {

    public Grid grid;

    @Override
    public void init(Account account) {
        super.init(account);
        grid = Grid.valueOf(account.getGridX(), account.getGirdY());
    }

    @Override
    public void syncInfo(FightAccount fightAccount) {
        fightAccount.setGrid(grid);
    }

    public static GridSyncStrategy valueOf(Grid grid) {
        GridSyncStrategy strategy = new GridSyncStrategy();
        strategy.grid = grid;
        return strategy;
    }
}
