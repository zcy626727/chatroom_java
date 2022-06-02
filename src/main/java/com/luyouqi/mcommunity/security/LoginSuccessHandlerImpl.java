package com.luyouqi.mcommunity.security;

import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功
 */
public class LoginSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        System.out.println(authentication);
//        System.out.println(authentication.getAuthorities());//[ROLE_普通用户]
//        System.out.println(authentication.getCredentials());//null
//        System.out.println(authentication.getDetails());//WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null]
//        System.out.println(authentication.getPrincipal());//返回查询到的用户全部信息
        SecurityHandlerUtil.responseHandler(httpServletResponse,new Result(ResultArgs.USER_LOGIN_SUCCESS_CODE, ResultArgs.USER_LOGIN_SUCCESS_MSG)
            .addKV("userInfo",authentication.getPrincipal()));
    }
}
