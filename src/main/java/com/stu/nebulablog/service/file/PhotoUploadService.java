package com.stu.nebulablog.service.file;

import com.stu.nebulablog.service.file.AbstractFileUploadService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class PhotoUploadService extends AbstractFileUploadService {

    @Override
    protected void doUploadPhoto(String prePath, MultipartFile multipartFile) {
        String path=prePath+ "ProfilePhoto.jpg";
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            int size = Math.min(image.getHeight(), image.getWidth());
            int percent = Math.min(1, 200 / size);
            int newWith = image.getHeight() * percent;
            int newHeight = image.getWidth() * percent;
            Thumbnails.of(multipartFile.getInputStream()).forceSize(newWith, newHeight).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
