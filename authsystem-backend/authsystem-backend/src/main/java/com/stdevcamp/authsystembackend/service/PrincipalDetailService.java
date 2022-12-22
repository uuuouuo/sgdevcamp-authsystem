package com.stdevcamp.authsystembackend.service;

import com.stdevcamp.authsystembackend.model.PrincipalDetails;
import com.stdevcamp.authsystembackend.model.entity.User;
import com.stdevcamp.authsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 유효한 사용자인지 확인하는 서비스
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
            Optional<User> optional = userRepository.findById(id);
            if(!optional.isPresent()) {
                new UsernameNotFoundException("Not found User !!!!");
            }
            return new PrincipalDetails(optional.get());
    }
}
