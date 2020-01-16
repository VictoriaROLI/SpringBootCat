package com.victoria.sys.common;


public interface Constast {

    //状态码
    public static final Integer OK= 200;
    public static final Integer ERROR= -1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD="123456";

    //菜单权限类型
    public static final String TYPE_MENU="menu";

    //菜单权限类型
    public static final String TYPE_PERMISSION="permission";

    //可用状态
    public static final Integer AVAILABLE_TRUE=1;
    //不可用状态
    public static final Integer AVAILABLE_FALSE=0;

    //超级用户
    public static final Integer USER_TYPE_SUPER=0;
    //普通用户
    public static final Integer USER_TYPE_NORMAL=1;


    public static final Integer OPEN_TRUE = 1;

    public static final Integer OPEN_FALSE = 0;

    //图片上传路径
    public static final String FILE_PATH = "E:/upload/";

    //默认图片
    public static final String DEFAULT_IMG = "userface1.jpg";



}
