package whu.notemind.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whu.notemind.backend.model.MenuItem;
import whu.notemind.backend.service.FileService;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 获取文件列表(名称)
     * @param plusFilePath
     * @return
     */
    @RequestMapping(
            value = "files",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<MenuItem> getFiles(@RequestParam(value = "plusFilePath", required = true) String plusFilePath,@RequestParam(value = "isTrash", required = true) Boolean isTrash,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        return fileService.getFile(plusFilePath, isTrash);
    }

    /**
     * 获取文件列表(日期)
     * @param plusFilePath
     * @return
     */
    @RequestMapping(
            value = "filesByDate",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<MenuItem> getFilesByDate(@RequestParam(value = "plusFilePath", required = true) String plusFilePath,@RequestParam(value = "isTrash", required = true) Boolean isTrash,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        return fileService.getFileByDate(plusFilePath, isTrash);
    }

    /**
     * 获取文件列表（大小）
     * @param plusFilePath
     * @return
     */
    @RequestMapping(
            value = "filesByLength",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<MenuItem> getFilesByLength(@RequestParam(value = "plusFilePath", required = true) String plusFilePath,@RequestParam(value = "isTrash", required = true) Boolean isTrash,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        return fileService.getFileByLength(plusFilePath, isTrash);
    }

    /**
     * 删除/放回文件
     * @param plusFilePath
     * @return
     */
    @RequestMapping(
            value = "backOrDeleteFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public void backOrDeleteFile(@RequestParam(value = "plusFilePath", required = true) String plusFilePath,HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        Boolean isExist = fileService.copyFile(plusFilePath);
        fileService.deleteFile(plusFilePath, isExist);
        return;
    }

    /**
     * 更新最近文件
     * @param fileName
     * @return
     */
    @RequestMapping(
            value = "recordFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<MenuItem> recordFile(@RequestParam(value = "fileName", required = true) String fileName,HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        fileService.recordFile(fileName);
        return fileService.readFile();
    }

    /**
     * 展现最近文件
     * @param
     * @return
     */
    @RequestMapping(
            value = "showRecordFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<MenuItem> showRecordFile(HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        return fileService.readFile();
    }

    /**
     * showMdFile
     * @param fileName
     * @return
     */
    @RequestMapping(
            value = "showMdFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String showMdFile(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        return fileService.showMdFile(fileName);
    }


    /**
     * 保存文件
     * @param data
     * @return
     */
    @RequestMapping(
            value = "saveFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public void downloadFIle(@RequestParam(value = "data", required = true) String data, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        fileService.saveFile(data);
    }


}
