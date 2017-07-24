package com.catv.p2p.util;

import com.catv.p2p.base.util.BidConst;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 处理文件上传
 */
public class UploadFileUtils {

    /**
     * 处理上传文件
     * @param file 上传的文件
     * @param basePath 保存的基本路径
     * @return 返回文件名
     */
    public static String getFileName(MultipartFile file,String basePath){
        //生成文件名
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        //文件保存的路径
        File targetFile = new File(basePath,fileName);
        //公共文件存放路径
        File publicFile = new File(BidConst.PUBLIC_IMG_SHARE_PATH,fileName);
        try {
            //保存上传文件
            FileUtils.writeByteArrayToFile(targetFile,file.getBytes());
            //保存一份到公共文件夹
            FileUtils.writeByteArrayToFile(publicFile,file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
