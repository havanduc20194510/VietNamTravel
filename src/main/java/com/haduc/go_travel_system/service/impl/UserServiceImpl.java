package com.haduc.go_travel_system.service.impl;

import com.haduc.go_travel_system.dto.request.CreateUserRequest;
import com.haduc.go_travel_system.dto.request.UpdateUserRequest;
import com.haduc.go_travel_system.dto.response.UserResponse;
import com.haduc.go_travel_system.entity.User;
import com.haduc.go_travel_system.enums.ErrorCode;
import com.haduc.go_travel_system.enums.Role;
import com.haduc.go_travel_system.exception.AppException;
import com.haduc.go_travel_system.mapper.UserMapper;
import com.haduc.go_travel_system.repository.UserRepository;
import com.haduc.go_travel_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponse getMyProfile() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toDto(user);
    }
}
