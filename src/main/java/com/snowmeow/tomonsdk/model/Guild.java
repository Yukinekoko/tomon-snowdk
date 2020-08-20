package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

/** 群组
 * @author snowmeow
 * @date 2020/08/20
 * */
public class Guild {

    /* 群组ID */
    private String id;
    /* 群组名称 */
    private String name;
    /* 群组图标hash */
    private String icon;
    /* 群组图标url(?) */
    @SerializedName("icon_url")
    private String iconUrl;
    /* 群组背景图hash */
    private String background;
    /* 群组背景图url(?) */
    @SerializedName("background_url")
    private String backgroundUrl;
    /* 群组背景图属性 */
    @SerializedName("background_props")
    private String backgroundProps;
    /* 描述(?) */
    private String description;
    /* 群组位置 */
    private int position;
    /* 加入时间 */
    @SerializedName("joined_at")
    private String joinedAt;
    /* (?) */
    @SerializedName("updated_at")
    private String updatedAt;
    /* 所有者ID */
    @SerializedName("owner_id")
    private String ownerId;
    /* 系统频道ID */
    @SerializedName("system_channel_id")
    private String system_channelId;
    /* 系统频道Flag */
    @SerializedName("system_channel_flags")
    private int systemChannelFlags;
    /* 默认通知类型 */
    @SerializedName("default_message_notifications")
    private int defaultMessageNotifications;
    /* (?) */
    private boolean banned;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getBackgroundProps() {
        return backgroundProps;
    }

    public void setBackgroundProps(String backgroundProps) {
        this.backgroundProps = backgroundProps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSystem_channelId() {
        return system_channelId;
    }

    public void setSystem_channelId(String system_channelId) {
        this.system_channelId = system_channelId;
    }

    public int getSystemChannelFlags() {
        return systemChannelFlags;
    }

    public void setSystemChannelFlags(int systemChannelFlags) {
        this.systemChannelFlags = systemChannelFlags;
    }

    public int getDefaultMessageNotifications() {
        return defaultMessageNotifications;
    }

    public void setDefaultMessageNotifications(int defaultMessageNotifications) {
        this.defaultMessageNotifications = defaultMessageNotifications;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
