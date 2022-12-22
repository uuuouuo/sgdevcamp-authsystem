package com.stdevcamp.authsystembackend.service;

import com.stdevcamp.authsystembackend.config.jwt.JwtProvider;
import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import com.stdevcamp.authsystembackend.model.dto.LoginRequest;
import com.stdevcamp.authsystembackend.model.dto.UserResponse;
import com.stdevcamp.authsystembackend.model.entity.User;
import com.stdevcamp.authsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(JoinRequest request) {
        if(userRepository.findById(request.getId()) != null) {
            
        }
        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User user = new User();
        user.createUser(request, encPassword);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        Optional<User> optional = userRepository.findById(request.getId());

        // 나중에 수정 필요
        if (!optional.isPresent()) {
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        }

        User user = optional.get();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호 일치하지 않습니다.");
        }

        String jwtToken = jwtProvider.createToken(user);

        return jwtToken;

    }

    public String refresh(String id) {

        Optional<User> optional = userRepository.findById(id);

        // 나중에 수정 필요
        if (!optional.isPresent()) {
            throw new IllegalStateException("존재하지 않은 회원입니다.");
        }
        String newToken = jwtProvider.createToken(optional.get());

        return newToken;

    }

    public List<UserResponse> findUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> result = userList.stream()
                .map(u -> new UserResponse(u.getEmail(), u.getName()))
                .collect(Collectors.toList());
        return result;
    }
}
