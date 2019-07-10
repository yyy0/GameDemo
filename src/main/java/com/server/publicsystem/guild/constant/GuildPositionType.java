package com.server.publicsystem.guild.constant;

/**
 * @author yuxianming
 * @date 2019/7/4 10:44
 */
public enum GuildPositionType {

    /**
     * 会长
     */
    LEADER("会长", 1),

    /**
     * 副会长
     */
    DEPUTY("副会长", 2),

    /**
     * 精英
     */
    ELITE("精英", 3),

    /**
     * 会员
     */
    MEMBER("会员", 4),
    ;
    String position;

    int type;


    GuildPositionType(String position, int type) {
        this.type = type;
        this.position = position;
    }

    public static GuildPositionType typeOf(int type) {
        for (GuildPositionType positionType : values()) {
            if (positionType.getType() == type) {
                return positionType;
            }
        }
        return null;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
