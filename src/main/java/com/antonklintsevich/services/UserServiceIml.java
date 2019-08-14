package com.antonklintsevich.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antonklintsevich.entity.Authority;
import com.antonklintsevich.entity.Role;
import com.antonklintsevich.entity.User;
import com.antonklintsevich.persistense.DbUnit;
import com.antonklintsevich.persistense.UserRepository;

@Service
public class UserServiceIml implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session session = DbUnit.getSessionFactory().openSession();
        User user = userRepository.findByUsername(username, session);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        session.close();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getGrantedAuthorities(user.getRoles()));
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
//
//    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        Set<Authority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }
        return authorities.stream().map(authority->new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        
    }
  
}
