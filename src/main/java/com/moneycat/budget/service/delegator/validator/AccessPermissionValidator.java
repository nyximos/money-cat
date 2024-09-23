package com.moneycat.budget.service.delegator.validator;

import com.moneycat.core.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

@Service
public class AccessPermissionValidator {
    public void validate(Long targetUserId, Long userId) {
        if (!targetUserId.equals(userId)) {
            throw new UnauthorizedAccessException();
        }
    }
}
