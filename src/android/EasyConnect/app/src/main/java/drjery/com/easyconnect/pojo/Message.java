package drjery.com.easyconnect.pojo;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by DRJ on 2017/7/3.
 */

public class Message extends DataSupport implements Serializable {

    @SerializedName("direction")
    private int sender;

    private int userId;

    @SerializedName("deviceId")
    private int devId;

    @SerializedName("msgType")
    private int messageType;

    private long time;

    @SerializedName("msg")
    private String message;

    private int level;

    private int hasRead;

    public Message(){

    }

    public Message(int sender, int userId, int devId, int messageType, long time, String message, int level, int hasRead){
        this.sender = sender;
        this.userId = userId;
        this.devId = devId;
        this.messageType = messageType;
        this.time = time;
        this.message = message;
        this.level = level;
        this.hasRead = hasRead;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public long getTime() {
        return time;
    }
}
