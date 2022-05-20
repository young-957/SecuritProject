package com.resume.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 任恒德
 * @version 1.0
 * @date 2022/5/19 10:40
 * @description
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        auth.inMemoryAuthentication().withUser("rhd").password(encode).roles("admin");
    }

    //需要告诉spring有这个对象，否则会报错
    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
