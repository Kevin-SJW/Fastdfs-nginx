package com.siemens.fastdfs.service.impl;

import com.siemens.fastdfs.repository.FastdfsRepository;
import com.siemens.fastdfs.service.FastdfsService;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Service;

@Service
public class FastdfsServiceImpl implements FastdfsService {


    /**刚刚的配置文件位置
     *
     */
    public String conf_filename = "F:\\Java_projects\\fastdfs\\fastdfs_demo2\\fastdfs\\src\\main\\resources\\fdfs_client.conf";

    /**要上传的文件地址
     *
     */
    public String local_filename = "C:\\Users\\14241\\Desktop\\neo.jpg";

    /**
     * 文件路径
     */
    public String image_path = " ";

    /**
     * picture flow
     */
    private Byte picByte;

    private FastdfsRepository fastdfsRepository;

    private String SourceIpAddr;


//    @Override
//    public String upload(String local_filename) {
//
//        return FastdfsUtils.upload(local_filename);
//     }

//     @Override
//    public Fastdfs save(Fastdfs fastdfs){
//        Fastdfs newFastdfs = new Fastdfs();
//
//        newFastdfs.setId(newFastdfs.getId());
//        newFastdfs.setImagePath(newFastdfs.getImagePath());
//         return fastdfsRepository.save(fastdfs);
//     }

//    @Override
//    public String savePath(Fastdfs fastdfs){
//        Fastdfs newFastdfs = new Fastdfs();
//        newFastdfs.setId(newFastdfs.getId());
//        newFastdfs.setImagePath(newFastdfs.getImagePath());
//        return fastdfsRepository.savePath(fastdfs);
//
//    }


    @Override
    public Byte download(String image_path) {
        try {

            ClientGlobal.init(conf_filename);
//            FileOutputStream fos = null;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] picByte = storageClient.download_file("group1", image_path);
//            File file = new FileOutputStream(outFile);
            System.out.println(picByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picByte;
    }

    @Override
    public String getFileInfo(String image_path){
        try {
            ClientGlobal.init(conf_filename);
            String SourceIpAddr = "";
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            FileInfo fi = storageClient.get_file_info("group1", image_path);
            SourceIpAddr = fi.getSourceIpAddr();
            System.out.println(fi.getSourceIpAddr());
            System.out.println(fi.getFileSize());
            System.out.println(fi.getCreateTimestamp());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SourceIpAddr;

    }

//    @Override
//    public List<Fastdfs> getDbInfo(){
//        return fastdfsRepository.findAll();
//    }

//    @Override
//    public Fastdfs save(Fastdfs fastdfs){
//        return fastdfsRepository.save(fastdfs);
//
//    }


    @Override
    public void delete(String image_path){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            int i = storageClient.delete_file("group1", image_path);
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    }





