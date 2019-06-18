package com.whty.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xsl on 2019/6/10.
 */
@Controller
@RequestMapping("/")
public class ShiroController {

    @RequestMapping(value = {"/","/toLogin"})
    public String toLogin(){
        return "login";
    }

    @RequestMapping("a")
    public String testA(Model model){
        model.addAttribute("currentThread",JSONObject.toJSONString(Thread.currentThread()));
        model.addAttribute("currentUser",SecurityUtils.getSubject().getPrincipal());
        return "a";
    }
    @RequestMapping("b")
    public String testB(Model model){
        new Thread(()->{
            System.out.println(SecurityUtils.getSubject().getPrincipal());
        }).start();
        return "b";
    }
    @RequestMapping("login")
    @ResponseBody
    public String login(String userName,String password){
        JSONObject result = new JSONObject();
        try {
            if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
                result.put("retDesc","用户名或密码不能为空");
                result.put("retCode","000001");
            }else{
                UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
                token.setRememberMe(true);
                SecurityUtils.getSubject().login(token);
                result.put("retCode","000000");
                result.put("retDesc", "登录成功");
            }
        } catch (AuthenticationException ae) {
            try {
                result = JSONObject.parseObject(ae.getMessage());
            } catch (Exception e) {
                result.put("retDesc", ae.getMessage());
                result.put("retCode","000000");
            }
        }
        return result.toJSONString();
    }

    @RequestMapping("logout")
    @ResponseBody
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "退出成功";
    }
}
