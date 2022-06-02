package com.luyouqi.mcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.ChatMessage;
import com.luyouqi.mcommunity.entity.Chatroom;
import com.luyouqi.mcommunity.mapper.ChatMessageMapper;
import com.luyouqi.mcommunity.service.ChatMessageService;
import com.luyouqi.mcommunity.service.ChatroomService;
import com.luyouqi.mcommunity.utils.Result;
import com.luyouqi.mcommunity.utils.ResultArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zcy
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/chatMessage")
public class ChatMessageController {

    @Autowired(required = false)
    private ChatMessageMapper chatMessageMapper;


    // 将消息存入数据库
//            chatMessageMapper.insert(new ChatMessage(content,1,senderId,targetId,new Date()));
//            chatMessageMapper.insert(new ChatMessage(content,0,senderId,targetId,new Date()));
    @PostMapping("/saveMessage")
    public Result saveMessage(@RequestBody ChatMessage chatMessage){
        if (chatMessageMapper.insert(chatMessage)==1){
            return new Result(ResultArgs.SUCCESS_CODE,"保存成功");
        }
        return new Result(ResultArgs.FAILURE_CODE,"保存失败");
    }

    @GetMapping("/getHistoryByUser/{type}/{targetId}/{senderId}")
    public Result getHistoryByUser(@PathVariable("type") Integer type,@PathVariable("targetId") String targetId,@PathVariable("senderId") String senderId){
        QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();

        wrapper.eq("type",type);
        if(type==0){//私聊
            wrapper.and(qw->{
                qw.or(wq->{
                    wq.eq("target_id",targetId);
                    wq.eq("sender_id",senderId);
                });
                qw.or(wq->{
                    wq.eq("target_id",senderId);
                    wq.eq("sender_id",targetId);
                });
            });
        }else {//群聊
            wrapper.eq("target_id",targetId);
        }

        List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
        return new Result(ResultArgs.SUCCESS_CODE,"查询成功").addKV("chatMessages",chatMessages);
    }


}

