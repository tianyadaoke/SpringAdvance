package com.example.dockerdemo.a05;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.dockerdemo.a05.component.Bean5;
import com.example.dockerdemo.a05.mapper.Mapper1;
import com.example.dockerdemo.a05.mapper.Mapper2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.dockerdemo.a05.component")
public class Config {
    public Bean5 bean5(){
        return new Bean5();
    }

    @Bean
    public Bean1 bean1(){
        return new Bean1();
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
    @Bean(initMethod = "init")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;

    }
//   spring底层也是这么一个个实现的，我们可以用包扫描方式优化
//    @Bean
//    public MapperFactoryBean<Mapper1> mapper1(SqlSessionFactory sessionFactory){
//        MapperFactoryBean<Mapper1> factory=new MapperFactoryBean<>(Mapper1.class);
//        factory.setSqlSessionFactory(sessionFactory);
//        return factory;
//    }
//    @Bean
//    public MapperFactoryBean<Mapper2> mapper2(SqlSessionFactory sessionFactory){
//        MapperFactoryBean<Mapper2> factory=new MapperFactoryBean<>(Mapper2.class);
//        factory.setSqlSessionFactory(sessionFactory);
//        return factory;
//    }



}
