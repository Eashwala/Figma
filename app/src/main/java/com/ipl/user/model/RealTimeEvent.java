package com.ipl.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealTimeEvent {
//    @SerializedName("event")
//    private int event;
//
//    @SerializedName("params")
//    private JsonObject params;
//
//    public int getType() {
//        return event;
//    }
//
//   public String getUserId() {
//        return null;//userId;
//    }
//
//    public <T> T getParams(Class<T> type) {
//        return new Gson().fromJson(params.toString(), type);
//    }


    @SerializedName("msgType")
    @Expose
    private String msgType;
    @SerializedName("msgContent")
    @Expose
    private MsgContent msgContent;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public MsgContent getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(MsgContent msgContent) {
        this.msgContent = msgContent;
    }
}
