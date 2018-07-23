package indi.a9043.demo.config;

import indi.a9043.demo.security.*;
import indi.a9043.demo.service.TestUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private TestUserDetailsService testUserDetailsService;
    @Resource
    private TestAuthenticationEntryPoint testAuthenticationEntryPoint;
    @Resource
    private TestAuthenticationSuccessHandler testAuthenticationSuccessHandler;
    @Resource
    private TestAuthenticationFailureHandler testAuthenticationFailureHandler;
    @Resource
    private TestAccessDeniedHandler testAccessDeniedHandler;
    @Resource
    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Resource
    private TestAuthenticationFilter testAuthenticationFilter;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        /*
         * 自定义Provider
         * 自定义UserDetailsService
         * 自定义PasswordEncoder
         */
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(testUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
         * add 自定义provider
         */
        auth
                .authenticationProvider(daoAuthenticationProvider)
                .eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 关闭csrf保护
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 设置为无状态
                .and()
                .authorizeRequests()
                .antMatchers("/hello").permitAll() // 不受保护资源
                .anyRequest().authenticated() //受保护资源
                .and()
                .formLogin() //表单登录
                .loginProcessingUrl("/login") //表单登录api 不同于loginPage登录页面
                .successHandler(testAuthenticationSuccessHandler) //登录成功handler
                .failureHandler(testAuthenticationFailureHandler) //登录失败handler
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(testAuthenticationEntryPoint) //未授权入口
                .accessDeniedHandler(testAccessDeniedHandler) //拒绝入口
                .and()
                .addFilterBefore(testAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class); //添加token过滤, 处理带token请求
    }
}
