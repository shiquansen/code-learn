package com.sbzl.framework.core.utils.io;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.Deflater;

/**
 * 解压或者打包zip
 */

public class ZipUtils {

    private final static int BUFFER_SIZE= 1024 * 8 * 10;
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

    /**
     * 将文件压缩为zip格式
     * @param sourcePath 文件路径
     * @param zipPath 压缩包保存路径
     * @param fileName 文件名称不带后缀
     */
    public static boolean zip(String sourcePath,String zipPath,String fileName) throws IOException {
        String seperator=File.separator;

        File sourcefile=new File(sourcePath);

        File zipFile=new File(zipPath + seperator + fileName+".zip");
        if (!sourcefile.exists()){
            zipFile.createNewFile();
        }

        ZipOutputStream zos=null;
        try {
            zos=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            //设置压缩包注释
//           zos.setComment(comment);
            //启用压缩
            zos.setMethod(ZipOutputStream.DEFLATED);
            //压缩级别为最强压缩，但时间要花得多一点
            zos.setLevel(Deflater.BEST_COMPRESSION);
            //设置压缩编码
            zos.setEncoding("GBK");
            //判断路径是否为目录
            if (sourcefile.isFile()){//如果路径是文件，直接压缩
                String zname=fileName+seperator+sourcefile.getName();
                return fileToZip(sourcefile,zname,zos);
            }else if(sourcefile.isDirectory()){//如果路径是文件夹，遍历并压缩
                String zname=fileName+seperator+sourcefile.getName()+seperator;
                zos.putNextEntry(new ZipEntry(zname));
                return isFileOrDirectory(sourcefile,fileName,zos);
            }
            zos.closeEntry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (zos!=null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 递归目录
     * @param sourcefile
     * @param fileName
     * @param zos
     * @return
     */
    private static boolean isFileOrDirectory(File sourcefile,String fileName,ZipOutputStream zos) throws IOException {
        String seperator=File.separator;
        File[] files=sourcefile.listFiles();
        if (files!=null){
            for (File file:files){
                if (file.isFile()){
                    String zname=fileName+seperator+file.getName();
                    boolean flag= fileToZip(file,zname,zos);
                    if (!flag){
                        return false;
                    }
                }else if (file.isDirectory()){
                    String zname=fileName+seperator+file.getName()+seperator;
                    zos.putNextEntry(new ZipEntry(zname));
                    zos.closeEntry();
                    boolean flag=isFileOrDirectory(file,fileName+seperator+file.getName(),zos);
                    if (!flag){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**
     * 单个文件压缩
     * @param sourcefile 源文件路径
     * @param name 文件名
     * @param zos ZipOutputStream
     * @return
     */
    public static boolean fileToZip(File sourcefile,String name,ZipOutputStream zos){
        if (!sourcefile.exists()){
            return false;
        }
        String source=sourcefile.getPath().replace("\\","/");
        File file=new File(source);
        BufferedInputStream bis=null;
        try {
            bis=new BufferedInputStream(new FileInputStream(file));
            zos.putNextEntry(new ZipEntry(name));
            byte[] bys=new byte[BUFFER_SIZE];
            int len=-1;
            while ((len=bis.read(bys))!=-1){
                zos.write(bys,0,len);
                zos.flush();
            }
            zos.closeEntry();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



}
