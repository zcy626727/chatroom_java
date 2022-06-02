package com.luyouqi.mcommunity.mapper;

import com.luyouqi.mcommunity.entity.Chatroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-04-03
 */
public interface ChatroomMapper extends BaseMapper<Chatroom> {

    List<Chatroom> selectRoomsByUserId(String id);

}
