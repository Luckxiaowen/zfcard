package com.zf.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class UpLoadUtil {

    public static HashMap updateUserWxCode( HttpServletRequest request, MultipartFile file) {
        HashMap map=new HashMap();
        if(!Objects.isNull(file)){
            UUID id=UUID.randomUUID();//生成文件名
            try {
                //参数就是图片保存在服务器的本地地址
//              file.transferTo(new File(url+"\\src\\main\\resources\\static\\images\\"+id+".png"));

                file.transferTo(new File("/www/myproject/image/"+id+".png"));
                map.put("url",request.getServerName()+":"+request.getServerPort()+"/images/"+id+".png");
                map.put("msg",200);
                return map;
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("url","上传失败");
            map.put("msg",201);
            return map;
        }
        map.put("url","文件为空");
        map.put("msg",201);
        return map;
    }
}
