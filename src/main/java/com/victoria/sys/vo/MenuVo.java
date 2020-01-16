package com.victoria.sys.vo;

import com.victoria.sys.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVo extends Permission {

    private static final long serialVersionUID = 1L;

    private Integer page = 1 ;

    private Integer limit = 10;

//    private Integer[] ids;//接受多个id
//
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;


}
