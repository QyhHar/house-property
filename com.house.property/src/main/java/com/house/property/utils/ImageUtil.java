package com.house.property.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hang.qi
 * @program: property
 * @description: 图片上传工具类
 * @date 2020-12-24 17:42:10
 */
@Slf4j
public class ImageUtil {

    @Value("${upload.image.address}")
    private static String imagePath;

    /**
     * @Description:
     * @Author: hang.qi
     * @Date: 2020/12/24 0024 下午 5:43
     */
    public static String saveImage(@RequestParam(value = "file") MultipartFile file){
        try {
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件名后缀
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            suffix = suffix.toLowerCase();
            if(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif")){
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddhhmmss");
                String path = imagePath+format.format(new Date())+"/";
                String name = format1.format(new Date());
                log.info("path:" + path);
                log.info("name:" + name);
                fileName = name+suffix;
                File targetFile = new File(path, fileName);
                if(!targetFile.getParentFile().exists()){    //注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }
               return path+fileName;
            }else{
                log.error("图片格式不对");
            }

        }catch (Exception e){
            log.error("新增出错",e);
        }
        return null;
    }

}
