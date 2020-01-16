package com.victoria.sys.common;

import com.victoria.sys.pojo.User;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {

    private User user;


    private List<String> roles;

    private List<String> permissions;
}
