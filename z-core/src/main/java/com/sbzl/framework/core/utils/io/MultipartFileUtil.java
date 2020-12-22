package com.sbzl.framework.core.utils.io;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFileUtil {

    /**
     * 复制的路径
     * @param destDirPath
     * @param multipartFile
     * @return
     */
    public static File copyMultipartFileToProjectPath(String destDirPath, MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        final File file = new File(destDirPath + "\\" + fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 输出为流
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static InputStream copyMultipartFileToStream(MultipartFile multipartFile) throws IOException {
        return multipartFile.getInputStream();
    }


    /**
     * 获取上传文件名
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static String getMultipartFileName(MultipartFile multipartFile) throws IOException {
        return multipartFile.getOriginalFilename();
    }

}
