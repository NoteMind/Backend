package whu.notemind.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import whu.notemind.backend.model.MenuItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class FileService {

    public static String filepath;

    public static String trashFilePath;

    @Value("${filepath}")
    public void setFilepath(String filepath) {
        FileService.filepath = filepath;
    }

    @Value("${trashfilepath}")
    public void setTrashFilepath(String trashFilepath) {
        FileService.trashFilePath = trashFilepath;
    }

    /**
     * 获取文件列表(默认使用文件名称排序)
     * @param plusPath
     * @return
     */
    public static List<MenuItem> getFile(String plusPath, Boolean isTrash){
        File file;
        if(!isTrash){file = new File(filepath + plusPath);}
        else{file = new File(trashFilePath + plusPath);}
        File[] files=file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return -(o2.getName().compareTo(o1.getName()));
            }
        });
        List<MenuItem> menuItems1=new ArrayList<>();
        for (File file1 : files) {
            if(!file1.isHidden()){
                System.out.println(file1.toString());
                MenuItem menuItem=new MenuItem(file1.getPath(),file1.getName(),file1.isDirectory());
                menuItems1.add(menuItem);
            }
        }
        return menuItems1;
    }

    /**
     * 获取文件列表(使用文件日期排序)
     * @param plusPath
     * @return
     */
    public static List<MenuItem> getFileByDate(String plusPath, Boolean isTrash){
        File file;
        if(!isTrash){file = new File(filepath + plusPath);}
        else{file = new File(trashFilePath + plusPath);}
        File[] files=file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if(diff > 0) return -1;
                else if (diff == 0) return 0;
                else return 1;
            }
            public boolean equals(Object obj){return true;}
        });
        List<MenuItem> menuItems2=new ArrayList<>();
        for (File file1 : files) {
            if(!file1.isHidden()){
                System.out.println(file1.toString());
                MenuItem menuItem=new MenuItem(file1.getPath(),file1.getName(),file1.isDirectory());
                menuItems2.add(menuItem);
            }
        }
        return menuItems2;
    }

    /**
     * 获取文件列表(使用文件从大到小排序)
     * @param plusPath
     * @return
     */
    public static List<MenuItem> getFileByLength(String plusPath, Boolean isTrash){
        File file;
        if(!isTrash){file = new File(filepath + plusPath);}
        else{file = new File(trashFilePath + plusPath);}
        File[] files=file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File l1, File l2) {
                long diff = l1.length() - l2.length();
                if(diff > 0) return 1;
                else if (diff == 0) return 0;
                else return -1;
            }
            public boolean equals(Object obj){return true;}
        });
        List<MenuItem> menuItems3=new ArrayList<>();
        for (File file1 : files) {
            if(!file1.isHidden()){
                System.out.println(file1.toString());
                MenuItem menuItem=new MenuItem(file1.getPath(),file1.getName(),file1.isDirectory());
                menuItems3.add(menuItem);
            }
        }
        return menuItems3;
    }

    /**
     * 复制文件到trash文件夹
     * @param plusPath
     * @return
     */
    public static Boolean copyFile(String plusPath) throws IOException {
        File oldFile = new File(filepath + plusPath);
        File newFile = new File(trashFilePath + plusPath);
        if(oldFile.exists()){
            Files.copy(oldFile.toPath(), newFile.toPath());
            System.out.println("文件移动成功！");
            return true;
        }
        else{return false;}
    }

    /**
     * 删除文件
     * @param plusPath
     * @return
     */
    public static void deleteFile(String plusPath, Boolean isExist){
        if(isExist){
            File file = new File(filepath + plusPath);
            if(file.delete()){
                System.out.println("文件删除成功");
            }
            else if(file.isDirectory()) {
                System.out.println("文件夹不为空，不可删除");
            }
            else{
                System.out.println("文件删除失败");
            }
        }
        else {
            File file = new File(trashFilePath + plusPath);
            if(file.delete()){
                System.out.println("文件删除成功");
            }
            else{
                System.out.println("文件删除失败");
            }
        }
    }
}
