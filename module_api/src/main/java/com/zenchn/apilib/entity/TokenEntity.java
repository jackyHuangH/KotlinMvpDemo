package com.zenchn.apilib.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author HZJ
 */

public class TokenEntity {

    /**
     * access_token : b1ecf27cf2c25efa47f50c09d37e34b8
     * refresh_token : c576a1575d56a1e5f2e9d149becf1a2d
     * expires_in : 259200
     * scope : {
     * "auths":[ "app:farmhouse:assistant",
     * "app:farmhouse:assistant:login",//是否可登陆
     * "app:farmhouse:assistant:edit"],//是否可新增建筑或者修改建筑信息
     * "roles":["admin"],
     * "areas":["330000000000"]} //区域权限
     */


    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "refresh_token")
    private String refreshToken;
    @JSONField(name = "expires_in")
    private int expiresIn;
    @JSONField(name = "scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static class ScopeInfo {

        @JSONField(name = "auths")
        private List<String> auths;
        @JSONField(name = "roles")
        private List<String> roles;
        @JSONField(name = "areas")
        private List<String> areas;

        public List<String> getAuths() {
            return auths;
        }

        public void setAuths(List<String> auths) {
            this.auths = auths;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public List<String> getAreas() {
            return areas;
        }

        public void setAreas(List<String> areas) {
            this.areas = areas;
        }
    }

    public ScopeInfo getScopeInfo() {
        return JSON.parseObject(scope, ScopeInfo.class);
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                '}';
    }
}
