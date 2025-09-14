package com.arpit.todo_list.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {

    private Users user;

    public UserDetail(Users user) {
        System.out.println("UserDetail");
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities");
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        System.out.println("getPassword");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("getUsername");
        return user.getUsername();
    }
}
