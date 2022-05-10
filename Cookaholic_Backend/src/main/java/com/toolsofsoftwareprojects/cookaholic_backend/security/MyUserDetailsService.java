package com.toolsofsoftwareprojects.cookaholic_backend.security;

import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CookaholicUserRepo cookaholicUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CookaholicUser> oUser = cookaholicUserRepo.findByUsername(username);
        if (!oUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        CookaholicUser user = oUser.get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserTitle().toString()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}