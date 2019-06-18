package com.whty.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xsl on 2019/6/14.
 */
public class Tutorial {
    private static final transient Logger log = LoggerFactory.getLogger(Tutorial.class);

    public static void main(String[] args) throws Exception{
        log.info("My First Apache Shiro Application");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator=new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 获取当前用户
        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }
        // 登录获取授权、权限
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                System.out.println("当前线程id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName()+"-----"+JSONObject.toJSONString(currentUser));

            } catch (UnknownAccountException uae) {
                log.info("用户名 " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("密码 " + token.getPrincipal() + " 错误!");
            } catch (LockedAccountException lae) {
                log.info("账号 " + token.getPrincipal() + " 被锁.  ");
            }
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }
        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }
        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:weild")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }
        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            Subject newSubject=SecurityUtils.getSubject();
            System.out.println(newSubject.isAuthenticated());
            System.out.println("当前线程id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName()+"-----"+ JSONObject.toJSONString(newSubject));
        }).start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            Subject newSubject=SecurityUtils.getSubject();
            System.out.println(newSubject.isAuthenticated());
            System.out.println("当前线程id:"+Thread.currentThread().getId()+",名称"+Thread.currentThread().getName()+"-----"+JSONObject.toJSONString(newSubject));
        }).start();

        Thread.sleep(500000000);
        //currentUser.logout();
        //System.exit(0);
    }
}
