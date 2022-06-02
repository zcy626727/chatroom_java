package com.luyouqi.mcommunity.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
class myMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("自动填充时间");
        this.setFieldValByName("regTime",new Date(),metaObject);//create_time
        this.setFieldValByName("createTime",new Date(),metaObject);//applyTime
        this.setFieldValByName("applyTime",new Date(),metaObject);//sendTime
        this.setFieldValByName("sendTime",new Date(),metaObject);//
//        this.setFieldValByName("",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("自动更新时间");
        this.setFieldValByName("createTime",new Date(),metaObject);
//        this.setFieldValByName("",new Date(),metaObject);
    }
}
