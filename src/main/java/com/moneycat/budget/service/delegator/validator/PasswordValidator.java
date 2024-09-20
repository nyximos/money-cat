package com.moneycat.budget.service.delegator.validator;

import com.moneycat.budget.controller.model.request.LoginRequest;
import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.budget.persistence.repository.entity.UserEntity;
import com.moneycat.core.exception.InvalidPasswordException;
import com.moneycat.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidator implements LoginValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void validate(LoginRequest target) {
        UserEntity user = userRepository.findByEmail(target.email()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(target.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }
    }
}
