# QueryDSL

## 简介

1. QueryDSL仅仅是一个通用的查询框架，专注于通过Java API构建类型安全的SQL查询。
2. Querydsl可以通过一组通用的查询API为用户构建出适合不同类型ORM框架或者是SQL的查询语句，也就是说QueryDSL是基于各种ORM框架以及SQL之上的一个通用的查询框架。
3. 借助QueryDSL可以在任何支持的ORM框架或者SQL平台上以一种通用的API方式来构建查询。目前QueryDSL支持的平台包括JPA，JDO，SQL，Java Collections，Mongodb，Lucene
4. 官网地址：[点击进入](http://www.querydsl.com/)
5. github地址：[点击进入](https://github.com/querydsl/querydsl)

## p6spy

**优点**

+ 记录SQL语句的执行时间戳

+ 记录SQL语句类型

+ 记录SQL填入参数的和没有填入参数的SQL语句

+ 根据配置的时间控制SQL语句的执行时间，对超出时间的SQL语句输出到日志文件中



**原理**

`P6Spy`通过劫持`JDBC`驱动，在调用实际`JDBC`驱动前拦截调用的目标语，达到`SQL`语句日志记录的目的



**应用**

- 替换你的`JDBC Driver`为`com.p6spy.engine.spy.P6SpyDriver`
- 修改`JDBC Url`为`jdbc:p6spy:xxxx`
- 配置`spy.properties`
- 添加自定义日志打印类

## 配置到项目

在Spring环境下，我们可以通过两种风格来使用QueryDSL。

一种是使用`JPAQueryFactory`的原生QueryDSL风格，其功能的强大，支持更复杂的查询业务。甚至可以用来进行更新和删除操作。

另一种是基于Spring Data提供的`QueryDslPredicateExecutor<T>`的Spring-data风格，可以简化一些代码，使得查询更加优雅。

## 1、引入querydsl

1. 导包，不需要携带版本号，springboot已经集成

   ```xml
   <!--query dsl-->
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-jpa</artifactId>
   </dependency>
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-apt</artifactId>
       <scope>provided</scope>
   </dependency>
   <!--query dsl end-->
   
   <!-- jpa -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <!-- jpa end -->
   
   <!-- sql打印 -->
   <dependency>
       <groupId>p6spy</groupId>
       <artifactId>p6spy</artifactId>
       <version>3.8.5</version>
   </dependency>
   <!-- sql打印 end -->
   
    <!-- MySql数据库连接 -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   
   <!-- lombok -->
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <scope>provided</scope>
   </dependency>
   ```
   
2. 加入插件，让程序自动生成query type(查询实体，命名方式为："Q"+对应实体名)。

   ```xml
   <!--该插件可以生成querysdl需要的查询对象，执行mvn compile即可-->
   <plugin>
       <groupId>com.mysema.maven</groupId>
       <artifactId>apt-maven-plugin</artifactId>
       <version>1.1.3</version>
       <executions>
           <execution>
               <goals>
                   <goal>process</goal>
               </goals>
               <configuration>
                   <outputDirectory>target/generated-sources/java</outputDirectory>
                   <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
               </configuration>
           </execution>
       </executions>
   </plugin>
   ```

   执行**mvn compile**之后，会将带有`@Entity`注解的实体类在指定路径`target/generated-sources/java`下生成一个衍生的实体类，我们后面就是用这个衍生出来的实体类去构建动态查询的条件进行动态查询。

3. 注入bean

   ```java
   import com.querydsl.jpa.impl.JPAQueryFactory;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import javax.persistence.EntityManager;
   
   /**
    * @auther YangFan
    * @Date 2021/3/25 9:14
    */
   @Configuration
   public class JPAFactory {
       @Bean
       public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
           return new JPAQueryFactory(entityManager);
       }
   }
   ```

## 2、配置文件

+ `application.yml`

  使用`p6spy`我们可以直接查看到数据库执行的sql而不是预编译带?的SQL

  修改数据库连接驱动和url

  ```yml
  spring:
    datasource:
      driver-class-name: com.p6spy.engine.spy.P6SpyDriver
      url: jdbc:p6spy:mysql://localhost:3306/ly_test?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&useJDBCCompliantTimezoneShift=true&allowPublicKeyRetrieval=true
      username: root
      password: admin
    jpa:
      show-sql: false
      hibernate:
        ddl-auto: update
  ```

  `spring.jpa.hibernate.ddl-auto`的几个常用属性值：

  none：默认值，什么都不做，每次启动项目，不会对数据库进行任何验证和操作

  create：每次运行项目，没有表会新建表，如果表内有数据会被清空

  create-drop：每次程序结束的时候会清空表

  update：每次运行程序，没有表会新建表，但是表内有数据不会被清空，只会更新表结构

  validate：运行程序会校验数据与数据库的字段类型是否相同，不同会报错

+ `spy.properties`

  ```yml
  driverlist=com.mysql.cj.jdbc.Driver
  
  # determines if property file should be reloaded
  # Please note: reload means forgetting all the previously set
  # settings (even those set during runtime - via JMX)
  # and starting with the clean table
  # (default is false)
  #reloadproperties=false
  reloadproperties=true
  
  # specifies the appender to use for logging
  # Please note: reload means forgetting all the previously set
  # settings (even those set during runtime - via JMX)
  # and starting with the clean table
  # (only the properties read from the configuration file)
  # (default is com.p6spy.engine.spy.appender.FileLogger)
  #appender=com.p6spy.engine.spy.appender.Slf4JLogger
  appender=com.p6spy.engine.spy.appender.StdoutLogger
  #appender=com.p6spy.engine.spy.appender.FileLogger
  #appender=com.p6spy.engine.spy.appender.Slf4JLogger
  
  # class to use for formatting log messages (default is: com.p6spy.engine.spy.appender.SingleLineFormat)
  #logMessageFormat=com.p6spy.engine.spy.appender.SingleLineFormat
  #自定义日志格式，在类中定义
  logMessageFormat=com.ly.cloud.demo.strategy.P6SpyLogger
  
  databaseDialectDateFormat=yyyy-MM-dd HH:mm:ss
  
  excludecategories=info,debug,result,resultset
  ```

+ 自定义日志格式类`P6SpyLogger`

  ```java
  import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
  import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
  import org.hibernate.engine.jdbc.internal.Formatter;
  
  import java.text.SimpleDateFormat;
  import java.util.Date;
  
  /**
   * @auther YangFan
   * @Date 2021/3/25 10:12
   */
  public class P6SpyLogger implements MessageFormattingStrategy {
  
      private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
  
      private static final Formatter formatter;
  
      static {
          formatter = new BasicFormatterImpl();
      }
  
      @Override
      public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
          StringBuilder sb = new StringBuilder();
          return !"".equals(sql.trim()) ? sb.append(this.format.format(new Date())).append(" | took ").append(elapsed).append("ms | ").append(category).append(" | connection ").append(connectionId).append(formatter.format(sql)).append(";").toString()  : "";
      }
  }
  ```

## 3、创建实体类

`Actor`

```java
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
```

`Work`

```java
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

    @Column(name = "word_name", nullable = false, length = 128)
    private String workName;
}
```

执行`Maven`插件的`compile`就能在指定目录生成`QActor`类和`QWork`类。

## 4、创建Repository

需要继承 `QuerydslPredicateExecutor`

```java
public interface QuerydslRepository extends JpaRepository<Actor, Long>, QuerydslPredicateExecutor<Actor> {

}
```

## 5、使用QueryDSL

### 5.1、常用写法

```tsx
QActor qm = QActor.actor;
//查询字段-select()
List<String> nameList = queryFactory.select(qm.name).from(qm).fetch();
//查询实体-selectFrom()
List<Actor> memberList = queryFactory.selectFrom(qm).fetch();
//查询并将结果封装至vo中
List<ActorInfoVO> fetch = jpaQueryFactory.select(Projections.bean(ActorInfoVO.class, qActor.id, qActor.actorName, work.workName)).from(qActor).leftJoin(work).on(qActor.id.eq(work.id)).fetch();
//去重查询-selectDistinct()
List<String> distinctNameList = queryFactory.selectDistinct(qm.name).from(qm).fetch();
//获取首个查询结果-fetchFirst()
Actor firstActor = queryFactory.selectFrom(qm).fetchFirst();
//获取唯一查询结果-fetchOne()
//当fetchOne()根据查询条件从数据库中查询到多条匹配数据时，会抛`NonUniqueResultException`。
Actor anotherFirstActor = queryFactory.selectFrom(qm).fetchOne();
```

### 5.2、BooleanBuilder

一、单个BooleanBuilder

```java
BooleanBuilder builder = new BooleanBuilder();
//like
builder.and(qm.actorName.like('%'+"Jack"+'%'));
//equal示例
builder.and(qm.actorEmail.eq("0013@163.com"));
//between
builder.and(qm.actorAge.between(20, 30));

List<Actor> memberConditionList = queryFactory.selectFrom(qm).where(builder).fetch();
```

二、两个BooleanBuilder

```java
BooleanBuilder builder = new BooleanBuilder();
builder.and(qm.actorAge.between(20, 30));

BooleanBuilder builder2 = new BooleanBuilder();
builder2.or(qm.actorEmail.eq("0013@163.com"));
builder2.or(qm.actorEmail.eq("0014@163.com"));
builder.and(builder2);

List<Actor> memberComplexConditionList = queryFactory.selectFrom(qm).where(builder).fetch();
```

### 5.1、原生dsl查询

1. 直接根据条件查询

   ```java
   @RunWith(SpringRunner.class)
   @SpringBootTest
   @Slf4j
   public class QuerydslTest {
       @Autowired
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
   }
   ```

2. 查询所有并根据字段排序

   ```java
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
   ```

3. 分页查询，并根据字段排序

   ```java
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
   
           }
   }
   ```

4. 根据条件模糊查询，并指定某个字段的范围

   ```java
   /**
    * 根据条件模糊查询，并指定某个字段的范围
    */
   @Test
   public void testFindByLikeNameAndEmailAndBetweenAgeOrderById() {
       	QActor qActor = QActor.actor;
           List<Actor> actorList = jpaQueryFactory.selectFrom(qActor)
                   .where(
                           qActor.actorName.like("name%"),
                           qActor.actorEmail.like("email%"),
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
   ```

5. 两张表关联查询

   ```java
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
   ```

6. 两张表关联查询将结果封装至vo中

   ```java
   /**
    * 查询并将结果封装至vo中
    */
   @Test
   public void testFindTwoTableToVo() {
       QActor qActor = QActor.actor;
       QWork work = QWork.work;
       List<ActorInfoVO> fetch = jpaQueryFactory.select(Projections.bean(ActorInfoVO.class, qActor.id, qActor.actorName, work.workName)).from(qActor).leftJoin(work).on(qActor.id.eq(work.id)).fetch();
       System.err.println(fetch);
   }
   ```

### 5.2、jpa整合dsl查询

1. 模糊查询并分页排序

   ```java
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
           BooleanExpression expression = qActor.actorName.like("name%").and(qActor.actorEmail.like("email%"));
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
   ```

2. 动态查询并分页排序

   ```java
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
   ```

### 5.3、删除和修改

删除和修改需要添加`@Transactional`

1. 修改

   ```java
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
   ```

2. 删除

   ```java
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
   ```













