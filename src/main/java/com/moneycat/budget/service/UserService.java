package com.moneycat.budget.service;

import com.moneycat.budget.UserConverter;
import com.moneycat.budget.controller.model.request.SignUpRequest;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.budget.persistence.repository.entity.UserEntity;
import com.moneycat.budget.service.delegator.validator.DuplicateEmailValidator;
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

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        duplicateEmailValidator.validate(signUpRequest.email());
        UserEntity user = userConverter.convert(signUpRequest);
        user.updatePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
