package com.victoria.bus.vo;

import com.victoria.bus.pojo.Customer;
import com.victoria.sys.pojo.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {

    private static final long serialVersionUID = 1L;

    private Integer page = 1 ;

    private Integer limit = 10;

    private Integer[] ids;//接受多个id


}
