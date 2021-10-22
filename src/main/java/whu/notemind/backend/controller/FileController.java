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
     * 删除文件
     * @param plusFilePath
     * @return
     */
    @RequestMapping(
            value = "deleteFile",
            method = RequestMethod.GET
    )
    @ResponseBody
    public void deleteFile(@RequestParam(value = "plusFilePath", required = true) String plusFilePath,HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        Boolean isExist = fileService.copyFile(plusFilePath);
        fileService.deleteFile(plusFilePath, isExist);
        return;
    }

    /**
     * 上传文件
     * @param id
     * @return
     */
    @RequestMapping(
            value = "upload",
            method = RequestMethod.GET
    )
    @ResponseBody
    public void uploadFile(@RequestParam(value = "id", required = true) String id) {

    }


    /**
     * 下载文件
     * @param id
     * @return
     */
    @RequestMapping(
            value = "download",
            method = RequestMethod.GET
    )
    @ResponseBody
    public void downloadFIle(@RequestParam(value = "id", required = true) String id) {

    }


}
