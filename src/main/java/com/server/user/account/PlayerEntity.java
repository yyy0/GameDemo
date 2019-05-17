//package com.server.user.account;
//
//import javax.persistence.*;
//
///**
// * @author yuxianming
// * @date 2019/4/26 16:57
// */
//@Entity
//@Table(name = "player", schema = "game", catalog = "")
//public class PlayerEntity {
//    private int playerid;
//    private String accountid;
//    private String name;
//    private String level;
//    private Integer job;
//
//    @Id
//    @Column(columnDefinition = "playerid")
//    public int getPlayerid() {
//        return playerid;
//    }
//
//    public void setPlayerid(int playerid) {
//        this.playerid = playerid;
//    }
//
//    @Basic
//    @Column(name = "accountid")
//    public String getAccountid() {
//        return accountid;
//    }
//
//    public void setAccountid(String accountid) {
//        this.accountid = accountid;
//    }
//
//    @Basic
//    @Column(name = "name")
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Basic
//    @Column(name = "level")
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    @Basic
//    @Column(name = "job")
//    public Integer getJob() {
//        return job;
//    }
//
//    public void setJob(Integer job) {
//        this.job = job;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        PlayerEntity that = (PlayerEntity) o;
//
//        if (playerid != that.playerid) return false;
//        if (accountid != null ? !accountid.equals(that.accountid) : that.accountid != null) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (level != null ? !level.equals(that.level) : that.level != null) return false;
//        if (job != null ? !job.equals(that.job) : that.job != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = playerid;
//        result = 31 * result + (accountid != null ? accountid.hashCode() : 0);
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (level != null ? level.hashCode() : 0);
//        result = 31 * result + (job != null ? job.hashCode() : 0);
//        return result;
//    }
//}
