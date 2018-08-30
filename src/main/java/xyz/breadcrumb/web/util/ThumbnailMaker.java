// 이 클래스는 썸네일 이미지를 생성해주는 클래스이다.
package xyz.breadcrumb.web.util;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailMaker {

    // 너비, 높이, 저장 경로, 원본 파일 이름, 원본 파일 이름 뒤에 붙을 접미어를 파라미터로 받는다.
    public static String thumbnailMaker(
            int width, int height, String uploadDir, String filename, String suffix) {

        File image = new File(uploadDir + "//" + filename);
        File thumbnail = new File(uploadDir + "//" + filename +"_"+ suffix);
        
        if (image.exists()) { 
            thumbnail.getParentFile().mkdirs(); 
            try {
                Thumbnails.of(image).size(width, height).outputFormat("jpg").toFile(thumbnail);
            } catch (IOException e) {
               e.printStackTrace();
            }
        }

        return filename + "_" + suffix;
    }
}
