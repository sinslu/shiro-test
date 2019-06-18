package com.whty.shiro.config;

import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm myRealm(){
        return new MyRealm();
    }
    @Bean
    public Realm myRealm2(){
        return new MyRealm2();
    }

    @Bean
    public Filter myAuthFilter(){
        return new RememberAuthenticationFilter();
    }
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SessionsSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filters=shiroFilterFactoryBean.getFilters();
        filters.put("authc",myAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);

        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return shiroFilterFactoryBean;
    }
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        //不需要登录的
        chainDefinition.addPathDefinition("/","anon");
        chainDefinition.addPathDefinition("/login","anon");
        chainDefinition.addPathDefinition("/a","authc");
        //chainDefinition.addPathDefinition("/b","authc");
        chainDefinition.addPathDefinition("/b","user");
        return chainDefinition;
    }
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间3*3600s
        sessionManager.setGlobalSessionTimeout(3*3600000L);
        return sessionManager;
    }
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(myRealm());
        List<Realm> realms=new ArrayList<>();
        realms.add(myRealm());
        realms.add(myRealm2());
        securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
}
