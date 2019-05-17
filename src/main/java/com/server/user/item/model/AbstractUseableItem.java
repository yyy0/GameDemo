package com.server.user.item.model;

import com.server.user.account.model.Account;

/**
 * @author yuxianming
 * @date 2019/5/13 15:52
 */
public abstract class AbstractUseableItem extends AbstractItem {

    public abstract void useEffect(Account account, int num);
}
