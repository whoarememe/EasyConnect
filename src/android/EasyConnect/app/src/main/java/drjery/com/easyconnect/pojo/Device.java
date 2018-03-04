package drjery.com.easyconnect.pojo;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by DRJ on 2017/7/3.
 */

public class Device extends DataSupport implements Serializable {

    private int deviceId;

    private int developerId;

    private String manufacturer;

    private String type;

    private String model;

    private int functionId;

    private String description;

    public Device(int deviceId,int developerId,String manufacturer,String type,String model,int functionId,String desciption){
        this.deviceId = deviceId;
        this.developerId = developerId;
        this.manufacturer = manufacturer;
        this.type = type;
        this.model = model;
        this.functionId = functionId;
        this.description = desciption;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
