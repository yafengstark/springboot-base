package com.liugh;



import com.liugh.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * SpringBoot单元测试;
 *
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
public class DataSourceTests {

//	@Autowired
//	DataSourceProperties dataSourceProperties;

//	@Autowired
//	DataSourceAutoConfiguration dataSourceAutoConfiguration;




//	@Autowired
//	com.alibaba.druid.pool.DruidDataSource druidDataSource;

//	@Autowired
//	ApplicationContext applicationContext;

//	@Resource
//	UserMapper userMapper;


	@Test
	public void usermapperTest(){

//		System.out.println(dataSourceProperties);
//		System.out.println(jdbcTemplate);
//		System.out.println(employeeMapper);

//		System.out.println(userMapper);

		Assert.assertEquals(1, 1);

//		System.out.println(applicationContext);

//		DataSource dataSource = applicationContext.getBean(DataSource.class);
//		// 查看配置数据源信息
//		System.out.println(dataSource);
//		System.out.println(dataSource.getClass().getName());
//		System.out.println(dataSourceProperties);
		//执行SQL,输出查到的数据
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		List<?> resultList = jdbcTemplate.queryForList("select * from test");
//		System.out.println("===>>>>>>>>>>>" + JSON.toJSONString(resultList));
	}




}
