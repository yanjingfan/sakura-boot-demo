package com.sakura.cloud.jpa.vo;

import lombok.Data;

/**
 * @auther YangFan
 * @Date 2021/7/12 23:35
 */
@Data
public class ActorInfoVO {

    private Long id;

    //演员名
    private String actorName;

    //演员作品
    private String workName;
}
