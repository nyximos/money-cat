package com.moneycat.budget.service.delegator.validator;

import com.moneycat.budget.persistence.repository.UserRepository;
import com.moneycat.core.exception.EmailAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateEmailValidator {

    private final UserRepository userRepository;

    public void validate(String email) {
        userRepository.findByEmail(email).ifPresent(o -> {throw new EmailAlreadyInUseException();});
    }
}
