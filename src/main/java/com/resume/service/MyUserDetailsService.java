package com.resume.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.entity.Users;
import com.resume.mapper.UserMapper;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * @author 任恒德
 * @version 1.0
 * @date 2022/5/19 11:10
 * @description
 */

@Service("userDetailsService")  //名字一致，方便注入
public class MyUserDetailsService implements UserDetailsService {



    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用userMapper方法，根据用户名查询数据库
        QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
        wrapper.eq("username",username);
        Users users = userMapper.selectOne(wrapper);

        if(users == null){ //无此用户，登陆失败
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
