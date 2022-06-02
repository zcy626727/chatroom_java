package com.luyouqi.mcommunity.service.impl;

import com.luyouqi.mcommunity.entity.ChatMessage;
import com.luyouqi.mcommunity.mapper.ChatMessageMapper;
import com.luyouqi.mcommunity.service.ChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zcy
 * @since 2021-04-16
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {


}
