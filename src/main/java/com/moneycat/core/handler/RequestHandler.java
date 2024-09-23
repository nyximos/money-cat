package com.moneycat.core.handler;

import com.moneycat.core.constant.MoneyCatConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    private final HttpServletRequest request;

    public boolean requiresAuthorization() {
        return Objects.nonNull(this.request.getHeader(MoneyCatConstants.HEADER_AUTHORIZE_TOKEN));
    }

}
