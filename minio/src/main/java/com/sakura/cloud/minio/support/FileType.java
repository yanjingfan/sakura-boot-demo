package com.sakura.cloud.minio.support;

/**
 * @auther YangFan
 * @Date 2022/3/29 21:29
 */
public enum FileType {

    IMG("IMG"),

    VIDEO("VIDEO"),

    FILE("FILE"),
    ;

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
