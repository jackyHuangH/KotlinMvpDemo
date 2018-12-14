package com.zenchn.apilib.entity;


import com.zenchn.apilib.util.CodecKit;

import java.io.Serializable;

/**
 *
 * @author HZJ
 */

public class LoginInfoEntity implements Serializable{

    private static final long serialVersionUID = -5984621455719340366L;

    private String username;
    private String password;
    private String deviceId;
    private String deviceName;

    public LoginInfoEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getEncryptPassword() {
        return password;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.password = encryptPassword;
    }

    public void setOrginPsw(String originPassword){
        this.password=originPassword;
    }

    public void setPassword(String password) {
        this.password = CodecKit.SHA256.encrypt(password);
    }

    @Override
    public String toString() {
        return "LoginInfoEntity{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

}
