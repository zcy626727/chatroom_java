package com.luyouqi.mcommunity.config;

import com.luyouqi.mcommunity.security.*;
import com.luyouqi.mcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    //redis 操作工具类
    private RedisTemplate redisTemplate;
    @Autowired
//    private PersistentTokenRepository tokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/register").permitAll()
//                .antMatchers("/user/hello").hasRole("普通用户")
                .antMatchers("/user/vip").hasRole("管理员")
                .anyRequest().authenticated()
                .and().cors().and().csrf().disable()
                .formLogin().loginProcessingUrl("/login")
//                .failureHandler(new LoginFailureHandlerImpl())
//                .successHandler(new LoginSuccessHandlerImpl())
                .and().logout().logoutSuccessHandler(new LogoutHandlerImpl()).logoutUrl("/logout")
                .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl())
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.rememberMe().tokenRepository(tokenRepository)
//                .tokenValiditySeconds(60*60)//有效时长
//                .userDetailsService(userService);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new Md5PasswordEncoder());
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {	
      CustomAuthenticationFilter filter = new CustomAuthenticationFilter();	
      filter.setAuthenticationSuccessHandler(new LoginSuccessHandlerImpl());
      filter.setAuthenticationFailureHandler(new LoginFailureHandlerImpl());
      filter.setAuthenticationManager(authenticationManagerBean());	
      return filter;
    }


    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**","/swagger-ui.html/**");
    }


}

