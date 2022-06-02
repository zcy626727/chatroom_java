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
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author zcy
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ApplyMessage对象", description="")
public class ApplyMessage implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "a_id", type = IdType.ASSIGN_ID)
    private String aId;

    @ApiModelProperty(value = "申请时间")
    @TableField(fill = FieldFill.INSERT)
    private Date applyTime;

    @ApiModelProperty(value = "申请者id")
    private String senderId;

    @ApiModelProperty(value = "申请目标id")
    private String targetId;

    @ApiModelProperty(value = "申请内容")
    private String content;

    @ApiModelProperty(value = "申请者姓名")
    private String senderName;

    //0为聊天申请
    @ApiModelProperty(value = "申请类型")
    private Integer type;


    public ApplyMessage() {
    }
}
