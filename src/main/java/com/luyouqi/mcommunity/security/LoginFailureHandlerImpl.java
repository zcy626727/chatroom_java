package com.luyouqi.mcommunity.security;

import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 */
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_NOT_EXIST_FAILURE_CODE, ResultArgs.USER_NOT_EXIST_FAILURE_MSG));
    }
}

