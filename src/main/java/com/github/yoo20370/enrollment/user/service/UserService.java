package com.github.yoo20370.enrollment.user.service;

import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.user.controller.response.CreateUserResponse;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.domain.UserRole;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import com.github.yoo20370.enrollment.user.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse create(CreateUserCommand command) {
        boolean isExist = userRepository.existsByEmail(command.getEmail());

        if (isExist) {
            throw new UserException(ErrorCode.USER_DUPLICATE_EMAIL);
        }

        User user = User.create(
            UserRole.from(command.getRole()),
            command.getEmail(),
            passwordEncoder.encode(command.getPassword()),
            command.getName(),
            command.getNickname()
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponse.of(
            savedUser.getId(),
            savedUser.getRole(),
            savedUser.getEmail(),
            savedUser.getName(),
            savedUser.getNickname(),
            savedUser.getActiveStatus()
        );
    }

}
