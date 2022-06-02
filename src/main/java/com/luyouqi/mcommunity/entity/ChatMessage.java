package com.luyouqi.mcommunity.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcy
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChatMessage对象", description="")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "消息id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "0为私聊,1为聊天室")
    private Integer type;

    @ApiModelProperty(value = "申请者姓名")
    private String senderName;

    @ApiModelProperty(value = "发送者")
    private String senderId;

    @ApiModelProperty(value = "接收者")
    private String targetId;

    @ApiModelProperty(value = "发送时间")
    @TableField(fill = FieldFill.INSERT)
    private Date sendTime;

}
