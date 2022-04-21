package com.takirahal.srfgroup.utils;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonUtil {

    /**
     *
     * @return
     */
    public static Resource loadDefaultFile() {
        try {
            String directoryDefaultUpload = System.getProperty("user.home") + "/srf-group/upload-dir/";
            Path rootLocation = Paths.get(directoryDefaultUpload);
            Path file = rootLocation.resolve("default_image00.jpg");
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return null;
        }
    }


    /**
     *
     * @param user
     * @return
     */
    public static String getFullNameUser(UserDTO user){
        return (!user.getFirstName().equals("") || !user.getLastName().equals("")) ? user.getFirstName()+" "+user.getLastName() : user.getEmail();
    }
}
