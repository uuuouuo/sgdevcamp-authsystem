package com.stdevcamp.authsystembackend.service;

import com.stdevcamp.authsystembackend.config.jwt.JwtProvider;
import com.stdevcamp.authsystembackend.exception.ErrorCode;
import com.stdevcamp.authsystembackend.exception.Message;
import com.stdevcamp.authsystembackend.exception.custom.NotFoundException;
import com.stdevcamp.authsystembackend.model.dto.JoinRequest;
import com.stdevcamp.authsystembackend.model.dto.LoginRequest;
import com.stdevcamp.authsystembackend.model.dto.UserResponse;
import com.stdevcamp.authsystembackend.model.entity.User;
import com.stdevcamp.authsystembackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public Map<String, Object> join(JoinRequest request) {

        if(userRepository.findById(request.getId()).isPresent()) {
            throw  new NotFoundException(ErrorCode.DUPLICATE_RESOURCE);
        }


        String rawPassword = request.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User user = new User();
        user.createUser(request, encPassword);
        userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("message", Message.USER_SAVE_SUCCESS_MESSAGE);

        return result;
    }
    @Override
    public String login(LoginRequest request) {

        Optional<User> optional = userRepository.findById(request.getId());

        if (!optional.isPresent()) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = optional.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(ErrorCode.INPUT_PASSWORD_MISMATCH.getMessage());
        }

        return jwtProvider.createToken(user);

    }

    @Override
    public String refresh(LoginRequest request) {
        Optional<User> optional = userRepository.findById(request.getId());

        if (!optional.isPresent()) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = optional.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(ErrorCode.INPUT_PASSWORD_MISMATCH.getMessage());
        }

        return jwtProvider.createToken(user);
    }

    @Override
    public Map<String, Object> findUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponseList = userList.stream()
                .map(u -> new UserResponse(u.getEmail(), u.getName()))
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("message", Message.USER_FIND_SUCCESS_MESSAGE);
        result.put("response", userResponseList);

        return result;
    }
}
