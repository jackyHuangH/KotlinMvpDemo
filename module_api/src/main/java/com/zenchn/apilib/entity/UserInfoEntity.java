package com.zenchn.apilib.entity;

import java.io.Serializable;

/**
 *
 * @author HZJ
 */

public class UserInfoEntity implements Serializable {

    private static final long serialVersionUID = 4075623417052041363L;
    /**
     * realName : 张三
     * sex : true
     * tel : 13844556677
     * email : abc@zenchn.com
     * portraitPath :
     */

    private String realName;
    private Boolean sex;
    private String tel;
    private String email;
    private String portraitPath;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "realName='" + realName + '\'' +
                ", sex=" + sex +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                ", portraitPath='" + portraitPath + '\'' +
                '}';
    }
}
