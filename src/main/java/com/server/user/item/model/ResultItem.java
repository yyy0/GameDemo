package com.server.user.item.model;

/**
 * @author yuxianming
 * @date 2019/5/29 18:22
 */
public class ResultItem {
    private AbstractItem item;
    private boolean success;

    public AbstractItem getItem() {
        return item;
    }

    public void setItem(AbstractItem item) {
        this.item = item;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
