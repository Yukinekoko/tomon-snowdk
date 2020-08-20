package com.snowmeow.tomonsdk.model;

/** 频道的权限覆盖
 * @author snowmeow
 * @date 2020/08/20
 * */
public class ChannelPermissionOverwrite {

    /* 设置的目标ID */
    private String id;
    /* 设置的目标类型 */
    private String type;
    /* 允许的权限 */
    private long allow;
    /* 拒绝的权限 */
    private long deny;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAllow() {
        return allow;
    }

    public void setAllow(long allow) {
        this.allow = allow;
    }

    public long getDeny() {
        return deny;
    }

    public void setDeny(long deny) {
        this.deny = deny;
    }
}
