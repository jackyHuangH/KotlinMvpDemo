package com.zenchn.apilib.entity;

/**
 *
 * @author HZJ
 */

public class PortraitEntity {

    private String portraitPath;

    public PortraitEntity() {
    }

    public PortraitEntity(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    @Override
    public String toString() {
        return "PortraitEntity{" +
                "portraitPath='" + portraitPath + '\'' +
                '}';
    }
}
