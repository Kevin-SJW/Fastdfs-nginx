package com.siemens.fastdfs.controller;
import com.siemens.fastdfs.domain.Fastdfs;
import com.siemens.fastdfs.repository.FastdfsRepository;
import com.siemens.fastdfs.service.FastdfsService;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author kevin
 * @version 1.0 2018/12/11
 * @description
 */



@Controller
public class FastdfsController {



    @Autowired
    FastdfsRepository fastdfsRepository;

    /**刚刚的配置文件位置
     *
     */
    public String conf_filename = "F:\\Java_projects\\fastdfs\\fastdfs_demo2\\fastdfs\\src\\main\\resources\\fdfs_client.conf";

    /**要上传的文件地址
     *
     */
    public String local_filename = "C:\\Users\\14241\\Desktop\\neo.jpg";

    /**
     * 文件返回路径
     */
    public String imagePath = null;

    /**
     * 组名
     */
    public String groupName = null;

    @Autowired
    @Resource(name = "myStorageClient1")
    private StorageClient1 storageClient1;

    public String uuid = null;

    public String full_address = null;


    @GetMapping("/upload_picture")
        public String file() {
            return "upload_picture";
        }

    /**
     * 上传文件
     * @param multipartFile
//     * @param httpServletRequest
     * @throws IOException
     * @throws MyException
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "multipartFile")MultipartFile multipartFile ) throws IOException, MyException {

        String fileExtName = multipartFile.getOriginalFilename().split("\\.")[1];
        String fileName = multipartFile.getOriginalFilename().split("\\.")[0];
        byte[] bytes = multipartFile.getBytes();
        // 随机产生uuid
        String uuid = UUID.randomUUID().toString();
        // 将下载的文件流保存
        IOUtils.write(bytes, new FileOutputStream("E:/images/"+ uuid +".jpg"));
        // 拼接服务器的文件路径
        String address = new String();
//        imagePath = address;
        address = ("http://172.20.10.4:8080"+ address);
        //设置文件元数据
        Map<String,String> metaList =  new HashMap<String, String>();
        metaList.put("fileName",fileName);
        metaList.put("fileExtName",fileExtName);
        NameValuePair[] nameValuePairs = null;
        if (metaList != null) {
            nameValuePairs = new NameValuePair[metaList.size()];
            int index = 0;
            for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<String,String> entry = iterator.next();
                String name = entry.getKey();
                String value = entry.getValue();
                nameValuePairs[index++] = new NameValuePair(name,value);
            }
        }
        //利用字节流上传文件
        String fileId = storageClient1.upload_file1(bytes, fileExtName,nameValuePairs);
        groupName = fileId.substring(0,6);
        System.out.println("uuid:"+uuid);
        /*组名*/
        System.out.println("组名："+groupName);
        /*路径名*/
        imagePath = fileId.substring(fileId.indexOf("/"));
        System.out.println("路径名："+imagePath);
        System.out.println("组名/路径名："+fileId);
        System.out.println("文件名："+fileName + '.' + fileExtName);
        System.out.println("字节流："+bytes);
        // 全路径
        System.out.println("全路径："+address+'/'+fileId);
        full_address = address+'/'+fileId;
        /*存mysql*/
        Fastdfs fastdfs = new Fastdfs();
        fastdfs.setGroupName(groupName);
        fastdfs.setImagePath(full_address);
        fastdfs.setFileName(fileName+'.'+fileExtName);
        fastdfsRepository.save(fastdfs);
        return "<img  src='"+full_address+"' /></br>"+ " 文件名为："+fileName + '.' + fileExtName+" </br>file_id: "+fileId;
    }

    @RequestMapping(value = "/downloadFile",method = RequestMethod.POST)
    @ResponseBody
    public String downloadFile(@RequestParam String fileId){
        byte[] content = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            Map<String,String> metaMap = getMetaList(fileId);
            content = storageClient1.download_file1(fileId);
            headers.setContentDispositionFormData("attachment",
                    new String(metaMap.get("fileName").getBytes("UTF-8"),"UTF-8"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 随机产生uuid
            String uuid = UUID.randomUUID().toString();
            // 将下载的文件流保存
            IOUtils.write(content, new FileOutputStream("E:/images/"+ uuid +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return "download success,the image has been saved at'E:/images/..jpg'";
    }


    /**
     * 删除文件
     * @param fileId
     * @return 删除失败返回-1，否则返回0
     */
    @RequestMapping(value = "/delFile",method = RequestMethod.DELETE)
    @ResponseBody
    public int delFile(@RequestParam("fileId") String fileId){
        try {
            return storageClient1.delete_file1(fileId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @PostMapping("/testUpload")
    @ResponseBody
    public String upload(String local_filename){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

//            NameValuePair nvp [] = new NameValuePair[]{};
            String fileIds[] = storageClient.upload_file(local_filename, "jpg", null);
            System.out.println(imagePath);
            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
            groupName = fileIds[0];

            /*获取文件名*/
            File tempFile =new File(local_filename.trim());
            String fileName = tempFile.getName();

            /*存mysql*/
            Fastdfs fastdfs = new Fastdfs();
            fastdfs.setGroupName(groupName);
            fastdfs.setImagePath("http://172.20.10.4:8080/"+fileIds[0]+"/"+imagePath);
            fastdfs.setFileName(fileName);

            fastdfsRepository.save(fastdfs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return "upload_picture";
    }

    /**
     * 获取文件元数据
     * @param fileId
     * @return
     */
    public  Map<String,String> getMetaList(String fileId){
        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 下载文件
     * @return 下载失败返回-1，否则返回uuid
     */
    @PostMapping("/testDownload")
    @ResponseBody
    public String download(String imagePath){
        byte[] b = new byte[0];
        try {
            //该参数都是通过上传的时候返回的参数获取的
            b = storageClient1.download_file("group1",imagePath);
            System.out.println(b.length);
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            // 将下载的文件流保存
            IOUtils.write(b, new FileOutputStream("E:/images/"+ uuid +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
//            return uuid;
        }
        return uuid;
    }




    /**
     * 删除文件
     * @return 删除失败返回-1，否则返回0
     */
    @PostMapping("/testDelete")
    @ResponseBody
    public String testDelete(String imagePath){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            int i = storageClient.delete_file("group1", imagePath);
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


}