package com.takirahal.srfgroup.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class ResizeImage {

    private final Logger log = LoggerFactory.getLogger(ResizeImage.class);

    @Value("${dynamicsvariables.heightAvatar}")
    private String heightAvatar;

    @Value("${dynamicsvariables.widthAvatar}")
    private String widthAvatar;

    @Value("${dynamicsvariables.maxHeightImgOffer}")
    private String maxHeightImgOffer;

    @Value("${dynamicsvariables.maxWidthImgOffer}")
    private String maxWidthImgOffer;

    @Async
    public void resizeAvatar(String path) {
        try {
            File input = new File(path);
            BufferedImage image = ImageIO.read(input);
            BufferedImage resized = resize(image, Integer.parseInt(heightAvatar), Integer.parseInt(widthAvatar));
            File output = new File(path);
            ImageIO.write(resized, "png", output);
        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
        }
    }

    @Async
    public void resizeImageOffer(String path) {
        try {
            log.info("Resize image for path: {}", path);
            File input = new File(path);
            BufferedImage image = ImageIO.read(input);
            int newWidthResize = image.getWidth();
            int newHightResize = image.getHeight();
            boolean isResize = false;
            if (newWidthResize > Integer.parseInt(maxWidthImgOffer)) {
                newWidthResize = Integer.parseInt(maxWidthImgOffer);
                isResize = true;
            }

            if (newHightResize > Integer.parseInt(maxHeightImgOffer)) {
                newHightResize = Integer.parseInt(maxHeightImgOffer);
                isResize = true;
            }

            if (isResize) {
                BufferedImage resized = resize(image, newHightResize, newWidthResize);
                File output = new File(path);
                ImageIO.write(resized, "png", output);

                // Add text for every image after resize
                addTextToImage(resized, path);
            }
            else{
                // Add text for every image without resize
                addTextToImage(image, path);
            }


        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
        }
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        try {
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
            return null;
        }
    }


    /**
     *
     * @param image
     */
    private void addTextToImage(BufferedImage image, String path){
        try {
            log.info("Add text to image : {}", path);
            // BufferedImage image = ImageIO.read(new File(path));
            Font font = new Font("Arial", Font.BOLD, 12);
            Graphics graphics = image.getGraphics();
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);
            graphics.drawString("SrfGroup", 40, 20);

            File output = new File(path);
            ImageIO.write(image, "png", output);
        }
        catch (Exception e){
            log.error("Exception whene add text to image : {}", e.getMessage());
        }
    }
}
