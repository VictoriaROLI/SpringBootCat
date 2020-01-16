package com.victoria.bus.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.victoria.bus.pojo.Goods;
import com.victoria.bus.pojo.Provider;
import com.victoria.bus.service.IGoodsService;
import com.victoria.bus.service.IProviderService;
import com.victoria.bus.vo.GoodsVo;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.DataGridView;
import com.victoria.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private IProviderService iProviderService;

    @RequestMapping("/loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo){
        Page<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVo.getProviderid()!=null&&goodsVo.getProviderid()!=0,"providerid",goodsVo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()), "goodsname", goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()), "productcode", goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()), "promitcode", goodsVo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description", goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()), "size", goodsVo.getSize());
        iGoodsService.page(page,queryWrapper);
        List<Goods> records = page.getRecords();

        for(Goods goods:records){
            Provider provider = iProviderService.getById(goods.getProviderid());
            goods.setProvidername(provider.getProvidername());
        }

        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("/addGoods")
    public ResultObj addGoods(GoodsVo goodsVo, HttpSession session){
        try {
            String fileName = (String) session.getAttribute("fileName");
            String newName = "";
            System.out.println("fileName"+fileName);
            if(fileName!=null){
                File file = new File(Constast.FILE_PATH+fileName);
                String[] tmps = fileName.split("_tmp");
                newName = tmps[0];
                goodsVo.setGoodsimg(newName);
                file.renameTo(new File(Constast.FILE_PATH+newName));
            }else{
                goodsVo.setGoodsimg(Constast.DEFAULT_IMG);
            }

            iGoodsService.save(goodsVo);

            //提交成功清空session的值
            session.removeAttribute("fileName");

            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    @RequestMapping("/updateGoods")
    public ResultObj updateGoods(GoodsVo goodsVo){
        try {
            iGoodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id){
        try {
            iGoodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("/loadAllGoodsForSelect")
    public DataGridView loadAllGoodsForSelect(){
        System.out.println("进来了");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Goods> list = iGoodsService.list(queryWrapper);
        for(Goods goods:list){
            Provider provider = this.iProviderService.getById(goods.getProviderid());
            if(null!=provider) {
                goods.setProvidername(provider.getProvidername());
            }
        }

        return new DataGridView(list);
    }

    @RequestMapping("/loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerid){
        List<Goods> list = iGoodsService.loadGoodsByProviderId(providerid);
        return new DataGridView(list);
    }

}
