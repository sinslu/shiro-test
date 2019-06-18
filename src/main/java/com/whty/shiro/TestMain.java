package com.whty.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by xsl on 2019/6/17.
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Subject newSubject= SecurityUtils.getSubject();
        System.out.println(newSubject.isAuthenticated());
    }
}
