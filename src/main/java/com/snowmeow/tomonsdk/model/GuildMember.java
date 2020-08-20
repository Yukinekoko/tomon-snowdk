package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

/** 群组成员
 * @author snowmeow
 * @date 2020/08/20
 * */
public class GuildMember {

    /* 用户信息 */
    private User user;
    /* 群昵称 */
    private String nick;
    /* 加入群组时间 */
    @SerializedName("joined_at")
    private String joinedAt;
    /* 角色列表 */
    private String[] roles;
    /* (?) */
    private boolean mute;
    /* (?) */
    private boolean deaf;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public void setDeaf(boolean deaf) {
        this.deaf = deaf;
    }
}
