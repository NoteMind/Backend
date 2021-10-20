package whu.notemind.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import whu.notemind.backend.model.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public static String filepath;

    @Value("${filepath}")
    public void setFilepath(String filepath) {
        FileService.filepath = filepath;
    }

    public static List<MenuItem> getFile(String plusPath){
        File file = new File(filepath + plusPath);
        File[] files=file.listFiles();
        List<MenuItem> menuItems=new ArrayList<>();
        for (File file1 : files) {
            System.out.println(file1.toString());
            MenuItem menuItem=new MenuItem(file1.getPath(),file1.getName(),file1.isDirectory());
            menuItems.add(menuItem);
        }
        return menuItems;
    }

}
