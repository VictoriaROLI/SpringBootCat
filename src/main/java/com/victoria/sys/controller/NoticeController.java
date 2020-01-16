package com.victoria.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import com.victoria.sys.pojo.Notice;
import com.victoria.sys.pojo.User;
import com.victoria.sys.service.INoticeService;
import com.victoria.sys.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-01
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private INoticeService iNoticeService;

    @RequestMapping("/loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){

        Page<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());

        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        iNoticeService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("/addNotice")
    public ResultObj addNotice(NoticeVo noticeVo, HttpServletRequest request){
        try {
            noticeVo.setCreatetime(new Date());
            User user = (User) request.getSession().getAttribute("user");
            noticeVo.setOpername(user.getName());
            iNoticeService.save(noticeVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateNotice")
    public ResultObj updateNotice(NoticeVo noticeVo){
        try {
            iNoticeService.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id){
        try {
            iNoticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("/batchDeleteNotice")
    public ResultObj batchDeleteNotice(NoticeVo noticeVo){
        try {
            List ids = new ArrayList();
            for(Integer id : noticeVo.getIds()) {
                ids.add(id);
            }
            iNoticeService.removeByIds(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }


    }


}
