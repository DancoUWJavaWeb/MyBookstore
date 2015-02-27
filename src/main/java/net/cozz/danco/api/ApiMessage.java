package net.cozz.danco.api;

/**
 * Created by costd035 on 2/26/15.
 */
public class ApiMessage {
    enum MsgType {
        INFO,
        ERROR
    }

    private MsgType msgType;
    private String message;


    public ApiMessage(MsgType msgType, String message) {
        this.msgType = msgType;
        this.message = message;
    }


    public MsgType getMsgType() {
        return msgType;
    }


    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
