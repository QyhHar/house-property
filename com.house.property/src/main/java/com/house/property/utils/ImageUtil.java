package com.house.property.utils;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Component
public class ImageUtil {

    private static String linuxImagePath;

    private static Boolean isWindows=false;

    @Value("${upload.image.imagePath}")
    public  void setPath(String imagePath) {
        this.linuxImagePath = imagePath;
    }

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
                String imagePath = "";
                String path = "";
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                String format2 = format.format(new Date());
                if(isWindows()){
                    imagePath = getRootPath();
                    imagePath = imagePath+".web\\static\\images\\";
                    path="images\\";
                }else {
                    imagePath = linuxImagePath+"/";
                    path=linuxImagePath+"/";
                }
                path = path+format2+"/";
                imagePath = imagePath+format2+"/";
                String name = format1.format(new Date());
                log.info("path:" + path);
                log.info("name:" + name);
                fileName = name+suffix;
                File targetFile = new File(imagePath, fileName);
                if(!targetFile.getParentFile().exists()){    //注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }
                file.transferTo(targetFile);
               return path+fileName;
            }else{
                log.error("图片格式不对");
            }

        }catch (Exception e){
            log.error("新增出错",e);
        }
        return null;
    }
    public static boolean isWindows() {
        if(isWindows){
            return true;
        }else if(System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
            isWindows=true;
            return true;
        }
        return false;
    }
    public static String getRootPath() {
        try {
            File file = new File("");
            return file.getCanonicalPath();
        }catch (Exception e){
            log.error("错误",e);
            return "";
        }

    }

}
