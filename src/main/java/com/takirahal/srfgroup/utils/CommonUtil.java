package com.takirahal.srfgroup.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonUtil {

    public static Resource loadDefaultFile() {
        try {
            String directoryDefaultUpload = System.getProperty("user.home") + "/srf-group/upload-dir/";
            Path rootLocation = Paths.get(directoryDefaultUpload);
            Path file = rootLocation.resolve("default_image.jpg");
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
