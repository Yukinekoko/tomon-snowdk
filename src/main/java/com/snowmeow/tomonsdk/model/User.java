package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    /* 角色ID */
    private String id;
    /* 用户名 */
    private String username;
    /* 用户后缀 */
    private String discriminator;
    /* 用户名(备用字段) */
    private String name;
    /* 用户头像文件名 */
    private String avatar;
    /* 用户头像链接(过时) */
    @SerializedName("avatarUrl")
    private String avatarUrl;
    /* 更新于 */
    @SerializedName("updated_at")
    private String updatedAt;
    /* 用户类型 */
    private int type;
    /* 用户邮箱 */
    private String email;
    /* 是否验证邮箱 */
    @SerializedName("email_verified")
    private boolean emailVerified;
    /* 用户手机号 */
    private long phone;
    /* 是否验证手机 */
    @SerializedName("phone_verified")
    private boolean phoneVerified;
    /* 是否被封禁 */
    private boolean banned;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }
}
