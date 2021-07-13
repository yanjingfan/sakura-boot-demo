package com.sakura.cloud.jpa.repository;

import com.sakura.cloud.jpa.entiry.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @auther YangFan
 * @Date 2021/7/12 23:18
 */
public interface QuerydslRepository extends JpaRepository<Actor, Long>, QuerydslPredicateExecutor<Actor> {
}
