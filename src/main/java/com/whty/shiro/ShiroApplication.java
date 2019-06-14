package com.whty.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by xsl on 2019/6/10.
 */
@SpringBootApplication
public class ShiroApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class,args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ShiroApplication.class);
    }
}
