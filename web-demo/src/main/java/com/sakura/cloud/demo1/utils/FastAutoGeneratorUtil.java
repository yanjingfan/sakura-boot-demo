package com.sakura.cloud.demo1.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * @auther yangfan
 * @date 2022/3/4
 * @describle MybatisPlus快速生成
 */
public class FastAutoGeneratorUtil {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://127.0.0.1:3306/sakura?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "admin");

    /**
     * 执行 run
     */
    public static void main(String[] args) {

        String[] tableNames = {"s_department", "s_department_menu",
                "s_department_resource", "s_menu",
                "s_resource", "s_resource_category",
                "s_role", "s_user",
                "s_user_department_middle", "s_user_role_middle"};

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author("yangfan")// 设置作者
                                .enableSwagger() // 开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .outputDir(".") // 指定输出目录，目前生成在当前目录下
                                .disableOpenDir()
                )

                // 包配置
                .packageConfig((scanner, builder) ->
                        builder.parent("com.sakura.cloud.sa") // 设置父包名
                                .moduleName("auth") // 设置父包模块名
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                            builder.addInclude(tableNames)
                                    .addTablePrefix("s_")// 忽略表前缀，生成文件的时候，不会包含T前缀
                                    .controllerBuilder()
                                    .enableRestStyle()
                                    .enableHyphenStyle()
                                    .entityBuilder()
                                    .logicDeleteColumnName("deleted")
                                    .enableLombok()
                                    .addTableFills(new Column("create_time", FieldFill.INSERT),
                                            new Column("update_time", FieldFill.UPDATE)).build();
                        }
                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }
}
