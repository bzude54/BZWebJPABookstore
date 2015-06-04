package edu.uwpce.bzbookstore.web;

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
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
