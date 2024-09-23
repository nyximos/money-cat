package com.moneycat.core.filter;

import com.moneycat.budget.service.TokenService;
import com.moneycat.core.handler.RequestHandler;
import com.moneycat.core.wrapper.TokenUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.moneycat.core.constant.MoneyCatConstants.HEADER_AUTHORIZE_TOKEN;
import static com.moneycat.core.constant.MoneyCatConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class TokenValidationFilter implements Filter {

    private final TokenService tokenService;
    private final RequestHandler requestHandler;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (requestHandler.requiresAuthorization()) {
            String token = httpServletRequest.getHeader(HEADER_AUTHORIZE_TOKEN);

            if (token != null && token.startsWith(TOKEN_PREFIX + StringUtils.SPACE)) {
                try {
                    TokenUser tokenUser = tokenService.validateToken(token);
                    httpServletRequest.setAttribute("TokenUser", tokenUser);
                } catch (Exception e) {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                    return;
                }
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization 헤더가 없거나 유효하지 않습니다.");
                return;
            }
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}