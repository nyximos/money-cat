package com.moneycat.budget.service;

import com.moneycat.budget.UserConverter;
import com.moneycat.budget.controller.model.request.LoginRequest;
import com.moneycat.budget.controller.model.request.SignUpRequest;
import com.moneycat.budget.controller.model.response.TokenResponse;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.budget.persistence.repository.entity.UserEntity;
import com.moneycat.budget.service.delegator.LoginValidationDelegator;
import com.moneycat.budget.service.delegator.validator.DuplicateEmailValidator;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final DuplicateEmailValidator duplicateEmailValidator;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginValidationDelegator loginValidationDelegator;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        duplicateEmailValidator.validate(signUpRequest.email());
        UserEntity user = userConverter.convert(signUpRequest);
        user.updatePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse signIn(LoginRequest loginRequest) {
        loginValidationDelegator.validate(loginRequest);
        UserEntity user = userRepository.findByEmail(loginRequest.email()).orElseThrow(UserNotFoundException::new);
        return tokenService.issue(user.getId(), loginRequest.email());
    }
}
