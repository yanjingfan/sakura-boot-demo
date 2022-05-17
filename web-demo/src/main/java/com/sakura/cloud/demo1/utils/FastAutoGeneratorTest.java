package com.sakura.cloud.demo1.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * @auther yangfan
 * @date 2022/3/4
 * @describle MybatisPlus快速生成
 */
public class FastAutoGeneratorTest {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://192.168.1.186:3306/sioc_3_0?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&useSSL=false", "emt", "chinaemt");

    /**
     * 执行 run
     */
    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author("yangfan")// 设置作者
//                                .enableSwagger() // 开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .outputDir(".") // 指定输出目录，目前生成在当前目录下
                                .disableOpenDir()
                )

                // 包配置
                .packageConfig((scanner, builder) ->
                        builder.parent("cn.sinvie.modules") // 设置父包名
                                .moduleName("special") // 设置父包模块名
                )
                // 策略配置
                .strategyConfig((scanner, builder) ->
                        builder.addInclude("special_module") // 多个表名用,隔开
//                                .addTablePrefix("t_") // 忽略表前缀，生成文件的时候，不会包含T前缀
                                .controllerBuilder().enableRestStyle().enableHyphenStyle()
                                .entityBuilder().enableLombok().addTableFills(new Column("update_time", FieldFill.INSERT))
                                .build()
                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }
}
