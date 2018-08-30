// 이 클래스는 base64로 인코딩 된 이미지 파일을 jpeg파일로 디코딩해주는 클래스이다.

package xyz.breadcrumb.web.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

public class Base64Decoder {
    // 파라미터로 base64로 인코딩 된 문자열과 저장할 주소를 받는다.
    public static boolean decoder(String base64, String path){
        
        String data = base64.split(",")[1];
        
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
        
        try {
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ImageIO.write(bufImg, "jpeg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
