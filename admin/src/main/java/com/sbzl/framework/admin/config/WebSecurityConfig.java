package com.sbzl.framework.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbzl.framework.admin.system.model.User;
import com.sbzl.framework.admin.system.service.MyUserService;
import com.sbzl.framework.admin.util.MyPasswordEncoder;
import com.sbzl.framework.admin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import java.io.PrintWriter;

@Order
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 放行策略
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers( "/web/*").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().and().logout().permitAll();
        http.csrf().disable();
    }

    /**
     * 设置权限放行拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Autowired(required = false)
    private MyUserService myUserService;

    @Autowired(required = false)
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService).passwordEncoder(myPasswordEncoder);
    }


//    @Bean
//    SessionRegistryImpl sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

//    @Bean
//    LoginFilter loginFilter() throws Exception {
//        LoginFilter loginFilter = new LoginFilter();
//        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    User user = (User) authentication.getPrincipal();
//                    user.setPassword(null);
//                    ResultVO ok = ResultVO.ok("登录成功!", user);
//                    String s = new ObjectMapper().writeValueAsString(ok);
//                    out.write(s);
//                    out.flush();
//                    out.close();
//                }
//        );
//        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    ResultVO respBean = ResultVO.error(exception.getMessage());
//                    if (exception instanceof LockedException) {
//                        respBean.setMsg("账户被锁定，请联系管理员!");
//                    } else if (exception instanceof CredentialsExpiredException) {
//                        respBean.setMsg("密码过期，请联系管理员!");
//                    } else if (exception instanceof AccountExpiredException) {
//                        respBean.setMsg("账户过期，请联系管理员!");
//                    } else if (exception instanceof DisabledException) {
//                        respBean.setMsg("账户被禁用，请联系管理员!");
//                    } else if (exception instanceof BadCredentialsException) {
//                        respBean.setMsg("用户名或者密码输入错误，请重新输入!");
//                    }
//                    out.write(new ObjectMapper().writeValueAsString(respBean));
//                    out.flush();
//                    out.close();
//                }
//        );
//        loginFilter.setAuthenticationManager(authenticationManagerBean());
//        loginFilter.setFilterProcessesUrl("/doLogin");
//        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
//        sessionStrategy.setMaximumSessions(1);
//        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
//        return loginFilter;
//    }
}