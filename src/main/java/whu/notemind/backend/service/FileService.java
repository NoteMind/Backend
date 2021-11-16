package whu.notemind.backend.service;

import org.springframework.stereotype.Service;
import whu.notemind.backend.model.MenuItem;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Service
public class FileService {

    public static String rootFilePath;

    public static String filepath;

    public static String trashFilePath;

    public static String recordPath;

    public FileService() {
        File rootfile=new File("");
        rootFilePath =rootfile.getAbsolutePath();
        filepath = rootFilePath + "/documents/";
        trashFilePath = rootFilePath + "/trash/";
        recordPath = rootFilePath + "/record.txt";
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
            System.out.println("文件移动到trash成功！");
            return true;
        }
        else{
            Files.copy(newFile.toPath(), oldFile.toPath());
            System.out.println("文件移动到documents成功！");
            return false;
        }
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
                File recordfile = new File(recordPath);
                recordfile.delete();
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
                File recordfile = new File(recordPath);
                recordfile.delete();
            }
            else if(file.isDirectory()) {
                System.out.println("文件夹不为空，不可删除");
            }
            else{
                System.out.println("文件删除失败");
            }
        }
    }

    /**
     * 使用 FileWriter 写文件
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException
     */
    public static void fileWriterMethod(String filepath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filepath, true)) {
            fileWriter.append(content);
        }
    }


    /**
     * 记录最近点击文件
     * @param fileName
     * @return
     */
    public static void recordFile(String fileName) throws IOException {
        String content = fileName + "\r\n";
        fileWriterMethod(recordPath, content);
        System.out.println("记录：" + content);
    }

    /**
     * 读取最近点击文件
     * @param
     * @return
     */
    public static List<MenuItem> readFile(){
        List<MenuItem> menuItems=new ArrayList<>();
        try {
            File file = new File(recordPath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String strLine = null;
            int lineCount = 1;
            while(null != (strLine = bufferedReader.readLine())){
                System.out.println("第[" + lineCount + "]行数据:[" + strLine + "]");
                lineCount++;
                File newFile = new File(filepath + "/" + strLine);
                MenuItem menuItem=new MenuItem(newFile.getPath(),newFile.getName(),newFile.isDirectory());
                System.out.println(menuItems.contains(menuItem));
                if(menuItems.contains(menuItem)){
                    menuItems.remove(menuItem);
                    System.out.println("delete" + menuItem.getName());
                }
                menuItems.add(0,menuItem);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return menuItems;
    }

    /**
     * 读取文件
     * @param fileName
     * @return
     */
    public static String showMdFile(String fileName) throws IOException {
        String content = "";
        String path = filepath + fileName;
        File file = new File(path);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    content += (char) tempchar;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(content);
        return content;
    }

    /**
     * 保存md文件
     * @param data
     * @return
     */
    public static void saveFile(String data) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        String path = filepath + date.toString() + ".md";
        File file = new File(path);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(data);
        fileWriter.close();
    }
}
