package com.victoria.sys.common;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj   {
    public static final ResultObj LOGIN_SUCCESS= new ResultObj(Constast.OK,"登录成功");
    public static final ResultObj LOGIN_ERROR_PASS= new ResultObj(Constast.ERROR,"登录失败,用户名或者密码错误");
    public static final ResultObj LOGIN_ERROR_CODE= new ResultObj(Constast.ERROR,"登录失败,验证码不正确");

    //删除
    public static final ResultObj DELETE_SUCCESS= new ResultObj(Constast.OK,"删除成功");
    public static final ResultObj DELETE_ERROR= new ResultObj(Constast.ERROR,"删除失败");


    //添加
    public static final ResultObj ADD_SUCCESS= new ResultObj(Constast.OK,"添加成功");
    public static final ResultObj ADD_ERROR= new ResultObj(Constast.ERROR,"添加失败");


    //修改
    public static final ResultObj UPDATE_SUCCESS= new ResultObj(Constast.OK,"修改成功");
    public static final ResultObj UPDATE_ERROR= new ResultObj(Constast.ERROR,"修改失败");


    //重置密码
    public static final ResultObj RESET_SUCCESS= new ResultObj(Constast.OK,"重置密码成功");
    public static final ResultObj RESET_ERROR= new ResultObj(Constast.ERROR,"重置密码失败");


    //分配
    public static final ResultObj DISPATCH_SUCCESS= new ResultObj(Constast.OK,"分配成功");
    public static final ResultObj DISPATCH_ERROR= new ResultObj(Constast.ERROR,"分配失败");


    //重置密码
    public static final ResultObj RESTPWD_SUCCESS = new ResultObj(Constast.OK,"重置密码成功");
    public static final ResultObj RESTPWD_ERROR = new ResultObj(Constast.ERROR,"重置密码失败");

    //两次密码不一致
    public static final ResultObj NEWPWD_CPWD_ERROR = new ResultObj(Constast.ERROR,"两次密码不一致");

    //上传文件
    public static final ResultObj FILE_UPLOAD_SUCCESS =  new ResultObj(Constast.OK,"上传成功");
    public static final ResultObj FILE_UPLOAD_ERROR =  new ResultObj(Constast.ERROR,"上传失败");


    //退货
    public static final ResultObj OPERATE_SUCCESS = new ResultObj(Constast.OK,"退货成功");
    public static final ResultObj OPERATE_ERROR = new ResultObj(Constast.ERROR,"退货失败");

    //退出登录
    public static final ResultObj LOGOUT_SUCCESS = new ResultObj(Constast.OK,"退出成功");


    private Integer code;
    private String msg;

}
