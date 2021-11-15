package com.sakura.cloud.jpa.entiry;

import lombok.Data;

import javax.persistence.*;

/**
 * @auther YangFan
 * @Date 2021/7/12 23:19
 */
@Entity
@Table(name = "t_work")
@Data
public class Work {

    /**
     * 主键生成采用数据库自增方式，比如MySQL的AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_name", nullable = false, length = 128)
    private String workName;
}