package com.luyouqi.mcommunity.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有
    public Result(Integer code,String msg) {
        this.setCode(code);
        this.setMessage(msg);
    }


    public Result addKV(String key, Object value){
        this.data.put(key, value);
        return this;
    }

}
