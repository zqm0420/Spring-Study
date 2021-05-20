package com.zqm.config;

import com.zqm.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@configuration代表这是一个配置类
public class MyConfig {

    //注册一个Bean，相当于xml中写的Bean标签
    //返回类型User相当于class属性
    //方法名user相当于id属性
    @Bean
    public User user(){
        return new User();
    }
}
