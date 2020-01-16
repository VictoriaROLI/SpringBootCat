package com.victoria.sys.controller;


import cn.hutool.core.lang.UUID;
import com.victoria.sys.common.Constast;
import com.victoria.sys.common.ResultObj;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/file")
@RestController
public class FileController {

    //文件上传
    @RequestMapping("/uploadFile")
    public Map<String,Object> uploadFile(MultipartFile  mf, HttpSession session) {

        String oldfileName = (String) session.getAttribute("fileName");
        if(null!=oldfileName){
            System.out.println("旧的图片"+oldfileName);
            File file = new File(Constast.FILE_PATH+oldfileName);
            file.delete();
        }


        Map<String ,Object> map = new HashMap<>();
        if(null==mf){
            map.put("code",-1);
            map.put("msg","上传失败");
            return map;
        }


        String[] splits = mf.getOriginalFilename().split("\\.");
        String  suffix = splits[splits.length-1];
        String fileName = UUID.randomUUID().toString();
        fileName = fileName+"."+suffix+"_tmp";
        String PathFileName = Constast.FILE_PATH + fileName;
        File uploadfile = new File(PathFileName);
        try {
            mf.transferTo(uploadfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("fileName",fileName);

        map.put("code",200);
        map.put("msg","上传成功");
        return map;
    }



}
