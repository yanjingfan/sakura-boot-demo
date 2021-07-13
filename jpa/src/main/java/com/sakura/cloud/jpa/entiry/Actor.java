package com.sakura.cloud.jpa.entiry;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @auther YangFan
 * @Date 2021/7/12 23:18
 */
@Entity
@Table(name = "t_actor")
@Data
public class Actor {

    /**
     * 主键生成采用数据库自增方式，比如MySQL的AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_name", nullable = false, length = 128, unique = true)
    private String actorName;

    @Column(name = "actor_age", nullable = false)
    private int actorAge;

    @Column(name = "actor_email", length = 64, unique = true)
    private String actorEmail;

    @Column(name = "create_time", nullable = false, length = 32)
    private String createTime = LocalDateTime.now().toString();
}
