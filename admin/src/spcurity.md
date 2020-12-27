1. 接入security包之后，所有url以及静态资源不能访问

2.默认用户 user 密码 后端生成且打印的UUID

3.三种配置密码方式以及密码加密设置
    1. 密码加密设置 实现 PasswordEncoder(upgradeEncoding方法是再加密，一般不用)
    2. 三种配置密码方式
        1. 内存
        2. 固定表结构
        3. 自定义（一般使用这个）
            ```
                implement WebSecurityConfigurerAdapter
                @Override
                protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                    auth.userDetailsService(myUserService).passwordEncoder(myPasswordEncoder);
                }
            ```
4. 静态资源以及URL放行，登录登出的注意事项
    1.URL放行以及登录登出
    ```
         @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    /**
                     *  放行的url
                     */
                    .antMatchers( "/web/*").permitAll()
                    /**
                     *  登陆配置 login.html（默认登陆页面）
                     *  name passwd 提交表单的双参
                     *  defaultSuccessUrl 和  successForwardUrl 登陆成功配置一个就行
                     *  failureForwardUrl 和  failureUrl 登陆失败配置一个就行
                     */
                        .and()
                        .formLogin()
                        .loginPage("/login.html")
                        .loginProcessingUrl("/doLogin")
                        .usernameParameter("name")
                        .passwordParameter("passwd")
                        .defaultSuccessUrl("/index")
                        .successForwardUrl("/index")
                        .permitAll()
                    /**
                     *  注销配置
                     *  deleteCookies 用来清除 cookie。
                     *  logoutRequestMatcher 方法不仅可以修改注销 URL，还可以修改请求方式，实际项目中，这个方法和 logoutUrl 任意设置一个即可。
                     *  默认注销的 URL 是 /logout，是一个 GET 请求，我们可以通过 logoutUrl 方法来修改默认的注销 URL。
                     *  clearAuthentication 和 invalidateHttpSession 分别表示清除认证信息和使 HttpSession 失效，默认可以不用配置，默认就会清除。
                     */
                        .and()
                        .logout()
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                        .logoutSuccessUrl("/index")
                        .deleteCookies()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                    .and().csrf().disable();
            }
    ```
    2. 静态资源放行
    ```
            @Override
            public void configure(WebSecurity web) throws Exception {
                web.ignoring().antMatchers("/js/**","/css/**","/images/**");
            }
    ```
    

    