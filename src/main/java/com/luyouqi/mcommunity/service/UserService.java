package com.luyouqi.mcommunity.service;

import com.luyouqi.mcommunity.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zcy
 * @since 2021-03-31
 */
public interface UserService extends IService<User>, UserDetailsService {

}
