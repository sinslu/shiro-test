package com.whty.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by xsl on 2019/6/14.
 */
public class RememberAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
/*        PrincipalCollection principals = new SimplePrincipalCollection("admin", "myRealm1");
        WebSubject.Builder builder = new WebSubject.Builder(SecurityUtils.getSecurityManager(),request, response);
        builder.principals(principals);
        builder.authenticated(true);
        WebSubject subject = builder.buildWebSubject();
        ThreadContext.bind(subject);*/
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
