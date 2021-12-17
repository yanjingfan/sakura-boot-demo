package com.sakura.cloud.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sakura.cloud.jpa.entiry.Actor;
import com.sakura.cloud.jpa.entiry.QActor;
import com.sakura.cloud.jpa.entiry.QWork;
import com.sakura.cloud.jpa.repository.QuerydslRepository;
import com.sakura.cloud.jpa.vo.ActorInfoVO;
import com.sakura.common.exception.YErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @auther YangFan
 * @Date 2021/7/12 23:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class QuerydslTest {
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * 直接根据条件查询
     */
    @Test
    public void testFindByActorNameAndActorEmail() {
        QActor qActor = QActor.actor;
        Actor actor = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.eq("嘀嘀嘀"),
                        qActor.actorEmail.eq("123456789@qq.com")
                )
                .fetchOne();
        try {
            String s = mapper.writeValueAsString(actor);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {

        }
    }

    /**
     * 查询所有并根据字段排序
     */
    @Test
    public void testFindAll() {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .fetch();
        try {
            String s = mapper.writeValueAsString(actorList);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {

        }
    }

    /**
     * 分页查询，并根据字段排序
     */
    @Test
    public void testFindByPagination() {
        int page = 1; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        QueryResults<Actor> actorQueryResults = jpaQueryFactory.selectFrom(qActor)
                .orderBy(
                        qActor.actorAge.asc()
                )
                .offset((page - 1) * pageSize)
                .limit(pageSize)
                .fetchResults();
        // 获取分页参数
        long total = actorQueryResults.getTotal();
        long totalPage = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, total, totalPage);
        List<Actor> actorListByPagination = actorQueryResults.getResults();
        try {
            String s = mapper.writeValueAsString(actorListByPagination);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {
            throw new YErrorException("解析json出错!");
        }
    }

    /**
     * 根据条件模糊查询，并指定某个字段的范围
     */
    @Test
    public void testFindByLikeNameAndEmailAndBetweenAgeOrderById() {
        QActor qActor = QActor.actor;
        List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                .where(
                        qActor.actorName.like("%name%"),
                        qActor.actorEmail.like("%email%"),
                        qActor.actorAge.between(20, 50)
                )
                .orderBy(
                        qActor.id.asc()
                )
                .fetch();
        try {
            String s = mapper.writeValueAsString(actorList);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {

        }
    }

    /**
     * 两张表关联查询
     */
    @Test
    public void testTwoTablesQuery() {
        QActor qActor = QActor.actor;
        QWork work = QWork.work;

        List<Tuple> fetch = jpaQueryFactory.select(qActor.id,qActor.actorName,qActor.actorAge,work.workName).from(qActor).leftJoin(work).on(qActor.id.eq(work.id)).orderBy(qActor.actorAge.desc()).fetch();
        System.err.println(fetch);
    }

    /**
     * 查询并将结果封装至vo中
     */
    @Test
    public void testFindTwoTableToVo() {
        QActor qActor = QActor.actor;
        QWork work = QWork.work;
        List<ActorInfoVO> fetch = jpaQueryFactory.select(Projections.fields(ActorInfoVO.class, qActor.id, qActor.actorName, qActor.actorAge, work.workName)).from(qActor).leftJoin(work).on(qActor.id.eq(work.id)).fetch();
        System.err.println(fetch);
    }


    //======================= QuerydslRepository ===========================================
    @Autowired
    private QuerydslRepository querydslRepository;

    /**
     * 模糊查询并分页排序
     */
    @Test
    public void testFindByActorNameAndActorEmailPagination() {
        int page = 0; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        // 模糊查询条件
        BooleanExpression expression = qActor.actorName.like("%name%").and(qActor.actorEmail.like("%email%"));
        // 排序、分页参数
        Sort sort = new Sort(Sort.Direction.DESC, "actorAge");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = querydslRepository.findAll(expression, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        try {
            String s = mapper.writeValueAsString(actorListByPagination);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {

        }
    }

    /**
     * 动态查询并分页排序
     */
    @Test
    public void testFindByDynamicQuery() {
        Integer actorAge = 45;
        String actorEmail = "email";
        String actorName = null;
        String createTime = "2020-11-21";

        int page = 0; // 第几页
        int pageSize = 10; // 每页大小

        QActor qActor = QActor.actor;
        // 初始化组装条件(类似where 1=1)
        Predicate predicate = qActor.isNotNull().or(qActor.isNull());

        //执行动态条件拼装
        // 相等
        predicate = actorAge == null ? predicate : ExpressionUtils.and(predicate, qActor.actorAge.eq(actorAge));
        // like 模糊匹配
        predicate = actorEmail == null ? predicate : ExpressionUtils.and(predicate, qActor.actorEmail.like(actorEmail + "%"));
        predicate = actorName == null ? predicate : ExpressionUtils.and(predicate, qActor.actorName.like(actorName + "%"));

        // 排序、分页参数
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page < 0 ? 0 : page, pageSize, sort);
        Page<Actor> actorPage = querydslRepository.findAll(predicate, pageRequest);
        log.info("分页查询第:[{}]页,pageSize:[{}],共有:[{}]数据,共有:[{}]页", page, pageSize, actorPage.getTotalElements(), actorPage.getTotalPages());
        List<Actor> actorListByPagination = actorPage.getContent();
        try {
            String s = mapper.writeValueAsString(actorListByPagination);
            log.info("s: {}", s);
        } catch (JsonProcessingException e) {

        }
    }

    /**
     * 修改
     *
     * 需要添加@Transactional，否则报错
     */
    @Transactional
    @Test
    public void testUpdate() {
        QWork work = QWork.work;
        long update = jpaQueryFactory.update(work).set(work.workName, "修改了之后的作品").where(work.id.eq(2l)).execute();
        System.err.println("update成功的条数" + update);
    }

    /**
     * 删除
     *
     * 需要添加@Transactional，否则报错
     */
    @Transactional
    @Test
    public void testDelete() {
        QWork work = QWork.work;
        long deleted = jpaQueryFactory.delete(work).where(work.id.eq(2l)).execute();
        System.err.println("delete成功的条数" + deleted);
    }
}
