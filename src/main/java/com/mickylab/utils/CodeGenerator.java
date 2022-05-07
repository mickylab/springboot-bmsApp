package com.mickylab.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * mp 代码生成器
 * by mickylab
 * @since 2022-03-20
 */
public class CodeGenerator {
    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/bms_app?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=true", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("mickylab") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/micky3k/Documents/GitHub/springboot-bmsApp/src/main/java/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.mickylab") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/micky3k/Documents/GitHub/springboot-bmsApp/src/main/resources/mybatis/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
                .execute();
    }

    public static void main(String[] args) {
        generate();
    }
}
