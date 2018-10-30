package com.wg.spring.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Author: wanggang
 * @Date: 2018/10/15 14:17
 * @todo
 */
@Controller
public class FileController {

    //@Resource
   // private UsersService usersService;

    @RequestMapping(value = "/goto",method = RequestMethod.GET)
    public String goUpload(){
        return "index";
    }
    //日志信息
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //文件上传相关代码
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("filename") MultipartFile file, @RequestParam("username") String username,
                         HttpServletRequest request){
        //判断文件是否为空
        if(file.isEmpty()){
            return "文件为空";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传文件名为："+fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("文件的后缀名为："+suffixName);
        //上传后存放路径,webapp/file
        String filePath = request.getSession().getServletContext().getRealPath("file/");
        logger.info("路径名："+filePath);
        //重命名文件名
        String fileNewName = fileName+System.currentTimeMillis()+suffixName;
        //创建文件目录
        File src = new File(filePath+fileNewName);
        //检查文件目录是否存在
        if(!src.getParentFile().exists()){
            src.getParentFile().mkdirs();
        }
        //上传文件
        try{
            file.transferTo(src);
            //上传文件保存到数据库
//            Users u = new Users();
//            logger.info("username="+username);
//            u.setUserName(username);
//            u.setPhoto("file/"+fileNewName);
//            this.usersService.insertUsers(u);
            return "上传成功！";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "上传失败!";
    }

    //文件下载代码
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ResponseBody
    public String downFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = "123.jpg";
        if(fileName != null){
            //获取当前路径下的文件
            String realPath = request.getServletContext().getRealPath("/file/")+fileName;
            File file = new File(realPath);
            //判断文件的对否存在
            if(file.exists()){
                //设置强制下载，不打开
                response.setContentType("application/x-msdownload");
                //设置文件名
                response.addHeader("Content-Disposition","attachment;fileName="+fileName);
                byte[]  bytes = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                try{
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int b = bis.read(bytes);
                    while(b != -1){
                        os.write(bytes,0,b);
                        b = bis.read(bytes);
                    }
                    os.close();
                    return "下载成功！";
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败！";
    }

    //多文件上传
    @RequestMapping(value = "/batch/upload",method = RequestMethod.POST)
    @ResponseBody
    public String MoreFileUpload(HttpServletRequest request){
        //获取上传文件list
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream bos = null;
        //遍历文件
        for(int i = 0; i < files.size();i++){
            file = files.get(i);
            //判断文件的是否存在
            if(!file.isEmpty()){
                try{
                    //获取文件名
                    String fileName = file.getOriginalFilename();
                    logger.info("上传文件名为："+fileName);
                    //获取文件后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    logger.info("文件的后缀名为："+suffixName);
                    //上传后存放路径,webapp/file
                    String filePath = request.getSession().getServletContext().getRealPath("file/");
                    //重命名文件名
                    String fileNewName = System.currentTimeMillis()+suffixName;
                    //System.out.println("当前文件名："+fileName);
                    //创建文件目录
                    File src = new File(filePath+fileNewName);
                    //检查文件目录是否存在
                    byte[] bytes = file.getBytes();
                    bos = new BufferedOutputStream(new FileOutputStream(src));
                    bos.write(bytes);
                    bos.close();
                }catch (Exception e){
                    bos = null;
                    return "上传失败原因是："+e.getMessage();
                }
            }
            return "上传失败！";
        }
        return "上传成功！";
    }

}
