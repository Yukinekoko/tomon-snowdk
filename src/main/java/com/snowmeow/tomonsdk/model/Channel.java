package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

/** 频道
 * @author snowmeow
 * @date 2020/08/20
 * */
public class Channel {

    /* 频道ID */
    private String id;
    /* 频道类型 */
    private int type;
    /* 所属群组ID */
    private String guild_id;
    /* 名称 */
    private String name;
    /* 群组内位置 */
    private int position;
    /* 权限覆盖 */
    @SerializedName("permission_overwrites")
    private ChannelPermissionOverwrite[] permissionOverwrites;
    /* 群组内所属分类 */
    @SerializedName("parent_id")
    private String parentId;
    /* 频道主题 */
    private String topic;
    /* 最后一条消息ID */
    @SerializedName("last_message_id")
    private String lastMessageId;
    /* (?) */
    @SerializedName("last_pin_timestamp")
    private String lastPinTimestamp;
    /* (?) */
    @SerializedName("default_message_notifications")
    private int defaultMessageNotifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ChannelPermissionOverwrite[] getPermissionOverwrites() {
        return permissionOverwrites;
    }

    public void setPermissionOverwrites(ChannelPermissionOverwrite[] permissionOverwrites) {
        this.permissionOverwrites = permissionOverwrites;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String getLastPinTimestamp() {
        return lastPinTimestamp;
    }

    public void setLastPinTimestamp(String lastPinTimestamp) {
        this.lastPinTimestamp = lastPinTimestamp;
    }

    public int getDefaultMessageNotifications() {
        return defaultMessageNotifications;
    }

    public void setDefaultMessageNotifications(int defaultMessageNotifications) {
        this.defaultMessageNotifications = defaultMessageNotifications;
    }
}
