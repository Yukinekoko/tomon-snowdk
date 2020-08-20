package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

/** 角色
 * @author snowmeow
 * @date 2020/08/20
 * */
public class Role {

    /* 角色的ID */
    private String id;
    /* 角色所属群组ID */
    @SerializedName("guild_id")
    private String guildId;
    /* 角色名称 */
    private String name;
    /* 颜色 */
    private int color;
    /* 权限 */
    private long permissions;
    /* 位置 */
    private int position;
    /* 是否在成员列表单独显示 */
    private boolean hoist;
    /* (类型未知?) */
    private String mentionable;
    /* (?) */
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getPermissions() {
        return permissions;
    }

    public void setPermissions(long permissions) {
        this.permissions = permissions;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isHoist() {
        return hoist;
    }

    public void setHoist(boolean hoist) {
        this.hoist = hoist;
    }

    public String getMentionable() {
        return mentionable;
    }

    public void setMentionable(String mentionable) {
        this.mentionable = mentionable;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
