package com.siemens.fastdfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.siemens.fastdfs.domain.Fastdfs;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;


public class TestFastDfs {

    /**刚刚的配置文件位置
     *
     */
    public String conf_filename = "F:\\Java_projects\\fastdfs\\fastdfs_demo2\\fastdfs\\src\\main\\resources\\fdfs_client.conf";

    /**要上传的文件地址
     *
     */
    public String local_filename = "C:\\Users\\14241\\Desktop\\neo.jpg";

//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
    /**
     * 文件路径
     */
    public String image_path = null;

    @Test
    public void testUpload() {

        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            NameValuePair nvp [] = new NameValuePair[]{};
            String fileIds[] = storageClient.upload_file(local_filename, "jpg", nvp);

            image_path = fileIds[1];
            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownload() {
        try {

            ClientGlobal.init(conf_filename);
//            FileOutputStream fos = null;
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//            NameValuePair nvp [] = new NameValuePair[]{};
//            String fileIds[] = storageClient.upload_file(local_filename, "jpg", nvp);
//            image_path = fileIds[1];
            byte[] b = storageClient.download_file("group1", "M00/00/01/rBQKBFwQBeKAUM-uAACmAb1rfa0299.jpg");
//            File file = new FileOutputStream(outFile);
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            // 将下载的文件流保存
            IOUtils.write(b, new FileOutputStream("E:/images/"+ uuid +".jpg"));
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFileInfo(){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//            NameValuePair nvp [] = new NameValuePair[]{};
//            String fileIds[] = storageClient.upload_file(local_filename, "jpg", nvp);
//            image_path = fileIds[1];
            FileInfo fi = storageClient.get_file_info("group1", "M00/00/00/rBQKBFwO5UOAXgSQAACmAb1rfa0386.jpg");
            System.out.println(fi.getSourceIpAddr());
            System.out.println(fi.getFileSize());
            System.out.println(fi.getCreateTimestamp());
            System.out.println(fi.getCrc32());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testDelete(){
        try {
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//            NameValuePair nvp [] = new NameValuePair[]{};
//            String fileIds[] = storageClient.upload_file(local_filename, "jpg", nvp);
//            image_path = fileIds[1];
            int i = storageClient.delete_file("group1", "M00/00/01/rBQKBFwQCeGAP9g5AACmAb1rfa0974.jpg");
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

