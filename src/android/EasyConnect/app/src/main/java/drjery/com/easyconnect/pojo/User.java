package drjery.com.easyconnect.pojo;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by DRJ on 2017/7/5.
 */

public class User extends DataSupport implements Serializable {

    private int id;

    private String name;

    private String phone;

    private String mail;

    public User(int userId,String name,String phone,String mail){
        this.id = userId;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int userId) {
        this.id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
