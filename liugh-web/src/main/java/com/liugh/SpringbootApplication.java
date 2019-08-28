package com.liugh;/*
 * Copyright (C) 2018 royal Inc., All Rights Reserved.
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/** 
 * @Description: 指定项目为springboot，由此类当作程序入口，自动装配 web 依赖的环境
 * @author liugh
 * @since 2018-05-03
 *
 */
@SpringBootApplication
@MapperScan("com.liugh.mapper")
@EnableCaching // 使用 @EnableCaching即可完成简单的缓存功能
public class SpringbootApplication  {
	public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
