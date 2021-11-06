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

    private static final int MAXSIZE = 100;

    @Override
    protected boolean doUploadFile(String prePath, MultipartFile multipartFile) {
        String path = prePath + "/ProfilePhoto.jpg";
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            int newWith = Math.min(image.getHeight(), MAXSIZE);
            int newHeight = Math.min(image.getWidth(), MAXSIZE);
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
