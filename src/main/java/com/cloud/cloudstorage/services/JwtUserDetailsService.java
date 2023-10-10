package com.cloud.cloudstorage.services;

import com.cloud.cloudstorage.entity.Users;
import com.cloud.cloudstorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users userEntity = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(String.format("User \"%s\" not found!", login)));
        return new User(userEntity.getLogin(), userEntity.getPassword(), new ArrayList<>());
    }
}
