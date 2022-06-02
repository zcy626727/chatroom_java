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
 * @since 2021-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Chatroom对象", description="")
public class Chatroom implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "聊天室id")
    @TableId(value = "r_id", type = IdType.ASSIGN_ID)
    private String rId;

    @ApiModelProperty(value = "聊天室名称")
    private String roomName;

    public Chatroom() {
    }

    @ApiModelProperty(value = "创建者id")
    private String uId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "最后活跃时间")
    @TableField(value = "last_msg_time",fill = FieldFill.INSERT_UPDATE)
    private Date lastMsgTime;

    public Chatroom(String roomName, String uId, String motto) {
        this.roomName = roomName;
        this.uId = uId;
        this.motto = motto;
    }

    @ApiModelProperty(value = "聊天室签名")
    private String motto;

    @ApiModelProperty(value = "聊天室头像")
    private String roomFace;

}
