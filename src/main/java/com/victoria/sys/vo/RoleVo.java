package com.victoria.sys.vo;

import com.victoria.sys.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo extends Role {

    private static final long serialVersionUID = 1L;

    private Integer page = 1 ;

    private Integer limit = 10;
}
