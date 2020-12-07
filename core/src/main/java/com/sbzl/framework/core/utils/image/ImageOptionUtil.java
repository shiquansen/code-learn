package com.sbzl.framework.core.utils.image;

import net.coobird.thumbnailator.Thumbnails;
import java.io.File;
import java.io.IOException;

public class ImageOptionUtil {

    /**
     * 生成缩略图
     * 只能按比例（长：宽），以宽为准
     */
    public static void generateThumbnail(String inPath, String outPath) throws IOException {
        File file = new File(outPath);
        if(file.exists()) file.delete();file.createNewFile();
        Thumbnails.of(inPath)
                .size(160, 80)
//                等比例缩放
//                .scale(0.25f)
//                水印位置,源文件,透明度
//                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("images/watermark.png")),0.5f)
//                裁剪（position(横纵坐标), size(长宽)）
//                .sourceRegion(Positions.BOTTOM_RIGHT,400,400)
//                转化图像格式
//                .outputFormat("png")
//                旋转
//                .rotate(90)
//                保持长宽比
//                .keepAspectRatio(false)
                .toFile(outPath);
    }

    /*
     *  功能不明的构造：
     *  crop
     *  allowOverwrite
     *  imageType
     *  scalingMode
     *  alphaInterpolation
     *  resizerFactory
     *  resizer
     *  dithering       抖动
     *  antialiasing    抗锯齿
     *  rendering       渲染
     *  outputQuality   图像品质
     */


    /*
     *  类中还有一些文件输出或者输出为图片流的操作，有需要可以自行提取
     */


    public static void main(String[] args) throws IOException {
        generateThumbnail("C:\\Users\\k0802365\\Desktop\\download.jpg","C:\\Users\\k0802365\\Desktop\\download-out.jpg");
    }


}
