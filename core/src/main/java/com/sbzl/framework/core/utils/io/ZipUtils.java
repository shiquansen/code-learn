package com.sbzl.framework.core.utils.io;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;

/**
 * 解压或者打包zip
 */

public class ZipUtils {
    private final static int BUFFER_SIZE= 1024 * 8 * 10;

    /**
     * 复制的路径
     * @param destDirPath
     * @param multipartFile
     * @return
     */
    private static File copyZIPToProjectPath(String destDirPath,MultipartFile multipartFile){
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
     * 解压文件
     * @param zipFile
     * @param outDir 输出路径
     * @throws IOException
     */
    public static void unZip(File zipFile, String outDir) throws IOException {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            outFileDir.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile,"GBK");
        for (Enumeration enumeration = zip.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir, zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
    }

}
