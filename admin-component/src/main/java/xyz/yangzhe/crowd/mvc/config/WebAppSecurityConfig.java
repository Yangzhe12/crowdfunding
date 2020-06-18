package xyz.yangzhe.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: SpringSecurity配置类
 * @Author: Yangzhe
 * @Data: 2020/6/18
 */
// 启用Web环境下权限控制功能
@EnableWebSecurity
// 表示当前类是一个配置类
@Configuration
// 启用全局方法权限控制功能，并且设置prePostEnabled = true。
// 保证@PreAuthority、@PostAuthority、@PreFilter、@PostFilter生效
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    // 提供密码验证
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 正式功能中使用基于数据库的认证
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()	// 对请求进行授权
                .antMatchers("/admin/to/login/page.html")	// 针对登录页进行设置
                .permitAll()			// 无条件访问
                .antMatchers("/bootstrap/**")	// 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/crowd/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/css/**")         // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/fonts/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/img/**")         // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/jquery/**")      // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/layer/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/script/**")      // 针对静态资源进行设置，无条件访问
                .permitAll()                    // 针对静态资源进行设置，无条件访问
                .antMatchers("/ztree/**")       // 针对静态资源进行设置，无条件访问
                .permitAll()
                .anyRequest()					// 其他任意请求
                .authenticated()				// 认证后访问
                .and()
                .csrf()							// 防跨站请求伪造功能
                .disable()						// 禁用
                .formLogin()					// 开启表单登录的功能
                .loginPage("/admin/to/login/page.html")	// 指定登录页面
                .loginProcessingUrl("/security/do/login.html")	// 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html")	// 指定登录成功后前往的地址
                .usernameParameter("username")	// 账号的请求参数名称
                .passwordParameter("password")	// 密码的请求参数名称
                .and()
                .logout()						// 开启退出登录功能
                .logoutUrl("/admin/do/logout.html")			// 指定退出登录地址
                .logoutSuccessUrl("/admin/to/login/page.html");	// 指定退出成功以后前往的地址
    }
}
