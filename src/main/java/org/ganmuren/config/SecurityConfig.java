package org.ganmuren.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//开启Spring Security的功能
public class SecurityConfig{

    @Bean
    PasswordEncoder passwordEncoder(){
        //测试用
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()//开启配置
                .antMatchers("/date").permitAll()
                .antMatchers("/hello").permitAll() //允许所有人访问
                .antMatchers("/user/**").hasRole("user") //访问/hello/**需要user角色
                .antMatchers("/admin/**").hasRole("admin") //访问/admin/**需要user角色
                .anyRequest().authenticated() //访问其他必须登录后访问
                .and().formLogin().loginProcessingUrl("/login").permitAll() //配置登录接口及允许访问
                .and().csrf().disable();//关闭csrf防护

        return http.build();
    }

    //配置用户
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    //配置访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//开启配置
                .antMatchers("/date").permitAll()
                .antMatchers("/hello").permitAll() //允许所有人访问
                .antMatchers("/user/**").hasRole("user") //访问/hello/**需要user角色
                .antMatchers("/admin/**").hasRole("admin") //访问/admin/**需要user角色
                .anyRequest().authenticated() //访问其他必须登录后访问
                .and().formLogin().loginProcessingUrl("/login").permitAll() //配置登录接口及允许访问
                .and().csrf().disable();//关闭csrf防护
    }*/

}
