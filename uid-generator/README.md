> 百度的开源ID生成算法。UidGenerator是Java实现的， 基于Snowflake算法的唯一ID生成器。UidGenerator以组件形式工作在应用项目中， 支持自定义workerId位数和初始化策略， 从而适用于docker等虚拟化环境下实例自动重启、漂移等场景。 在实现上， UidGenerator通过借用未来时间来解决sequence天然存在的并发限制； 采用RingBuffer来缓存已生成的UID, 并行化UID的生产和消费， 同时对CacheLine补齐，避免了由RingBuffer带来的硬件级「伪共享」问题. 最终单机QPS可达600万。 

如果手动建表，则可不依赖依赖`sakura-flyway`模块，建表脚本：[V1_1__init_20210510.sql](https://github.com/yanjingfan/sakura-boot/blob/master/sakura-uid-generator/src/main/resources/db/migration/V1_1__init_20210510.sql)

1. 添加依赖

   ```xml
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-uid-generator</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. 使用示例：参考`sakura-boot-demo`示例项目中的[UidController](https://github.com/yanjingfan/sakura-boot-demo/blob/master/src/main/java/com/sakura/cloud/demo1/controller/UidController.java)

   UidGenerator接口提供了 UID 生成和解析的方法，提供了两种实现: 

   +  DefaultUidGenerator	实时生成 
   +  CachedUidGenerator	生成一次id之后，按序列号+1生成一批id，缓存，供之后请求 

    如对UID生成性能有要求, 请使用CachedUidGenerator 

3. yml配置

   以下为可选配置, 如未指定将采用默认值

   ```yaml
   uid:
     timeBits: 30             # 时间位, 默认:30
     workerBits: 16           # 机器位, 默认:16
     seqBits: 7               # 序列号, 默认:7
     epochStr: "2019-02-20"   # 初始时间, 默认:"2019-02-20"
     enableBackward: true     # 是否容忍时钟回拨, 默认:true
     maxBackwardSeconds: 1    # 时钟回拨最长容忍时间（秒）, 默认:1
     CachedUidGenerator:      # CachedUidGenerator相关参数
       boostPower: 3          # RingBuffer size扩容参数, 可提高UID生成的吞吐量, 默认:3
       paddingFactor: 50      # 指定何时向RingBuffer中填充UID, 取值为百分比(0, 100), 默认为50
       #scheduleInterval: 60    # 默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒
   ```
