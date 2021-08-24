package com.stu.nebulablog.service.file.upload;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class PhotoUploadService extends AbstractFileUploadService {
    public PhotoUploadService(String preUrl) {
        super(preUrl);
    }

    @Override
    protected boolean doUploadFile(String prePath, MultipartFile multipartFile) {
        String path = prePath + "/ProfilePhoto.jpg";
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            int size = Math.min(image.getHeight(), image.getWidth());
            int percent = Math.min(1, 200 / size);
            int newWith = image.getHeight() * percent;
            int newHeight = image.getWidth() * percent;
            Thumbnails.of(multipartFile.getInputStream()).forceSize(newWith, newHeight).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean uploadPhoto(String prePath, MultipartFile multipartFile) {
        return super.uploadFile(prePath, multipartFile);
    }
}
