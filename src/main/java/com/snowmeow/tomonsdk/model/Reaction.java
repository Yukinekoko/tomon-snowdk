package com.snowmeow.tomonsdk.model;

/* 消息反应 */
public class Reaction {

    /* emoji消息 */
    private PartialEmoji emoji;
    /* 反应数量 */
    private int count;
    /* 自己是否参与了反应 */
    private boolean me;

    public PartialEmoji getEmoji() {
        return emoji;
    }

    public void setEmoji(PartialEmoji emoji) {
        this.emoji = emoji;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }
}
