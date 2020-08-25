package com.snowmeow.tomonsdk.model;

import com.google.gson.JsonObject;

/** WebSocket包
 * @author snowmeow
 * @date 2020/08/23
 * */
public class Payload {

    /* 操作码 */
    private int op;
    /* payload数据 */
    private JsonObject d;
    /* 事件类型 */
    private String e;

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public JsonObject getD() {
        return d;
    }

    public void setD(JsonObject d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
