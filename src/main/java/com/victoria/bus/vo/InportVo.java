package com.victoria.bus.vo;

import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Inport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class InportVo extends Inport {

    private static final long serialVersionUID = 1L;

    private Integer page = 1 ;

    private Integer limit = 10;

    private Integer[] ids;//接受多个id

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;



}
