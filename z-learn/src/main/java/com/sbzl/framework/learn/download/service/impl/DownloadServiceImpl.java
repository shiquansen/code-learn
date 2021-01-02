package com.sbzl.framework.learn.download.service.impl;

import com.sbzl.framework.learn.download.service.DownloadService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class DownloadServiceImpl implements DownloadService {
    @Override
    public void downloadDoc() throws IOException {

    try{
            File file = new File("C:\\Users\\k0802365\\Desktop\\语文平台接口.docx");
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            //获取response
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();        // 设置response的Header

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
