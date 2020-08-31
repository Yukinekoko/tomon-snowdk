package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {

    /* 消息ID */
    private String id;
    /* 消息类型 */
    private int type;
    /* 消息的频道ID */
    @SerializedName("channel_id")
    private String channelId;
    /* 消息的发送方，如果是null为系统消息 */
    private User author;
    /* 消息内容 */
    private String content;
    /* 用于去重的字段 */
    private String nonce;
    /* 发送时间 */
    private String timestamp;
    /* 修改时间 */
    @SerializedName("edited_timestamp")
    private String editedTimestamp;
    /* 是否置顶 */
    private boolean pinned;
    /* 消息的附带文件 */
    private Attachment[] attachments;
    /* 消息提及的人 */
    private User[] mentions;
    /* 消息的反应 */
    private Reaction[] reactions;
    /* 消息的表情 */
    private Stamp[] stamps;
    /* 消息的回复 */
    private Message reply;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment[] attachments) {
        this.attachments = attachments;
    }

    public User[] getMentions() {
        return mentions;
    }

    public void setMentions(User[] mentions) {
        this.mentions = mentions;
    }

    public Reaction[] getReactions() {
        return reactions;
    }

    public void setReactions(Reaction[] reactions) {
        this.reactions = reactions;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    public Stamp[] getStamps() {
        return stamps;
    }

    public void setStamps(Stamp[] stamps) {
        this.stamps = stamps;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getEditedTimestamp() {
        return editedTimestamp;
    }

    public void setEditedTimestamp(String editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }
}
