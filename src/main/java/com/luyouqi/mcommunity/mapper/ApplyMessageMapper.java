package com.luyouqi.mcommunity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyouqi.mcommunity.entity.ApplyMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyouqi.mcommunity.entity.UserRoom;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zcy
 * @since 2021-04-08
 */
public interface ApplyMessageMapper extends BaseMapper<ApplyMessage> {

    List<UserRoom> selectList(QueryWrapper<UserRoom> wrapper);
}
