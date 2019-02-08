package com.siemens.fastdfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siemens.fastdfs.domain.UserAuditorAware;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FastdfsApplication {


	public static void main(String[] args) {
		SpringApplication.run(FastdfsApplication.class, args);
		

	}

    @Bean(name = "myStorageClient1")
    public StorageClient1 storageClient1(){
        try {
            // 初始化文件资源
            ClientGlobal.init("F:\\Java_projects\\fastdfs\\fastdfs_demo2\\fastdfs\\" +
                    "src\\main\\resources\\fdfs_client.conf");
            // 链接FastDFS服务器，创建tracker和Storage
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            if(trackerServer == null){
                throw new IllegalStateException("无法连接到跟踪服务器");
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if(storageServer == null){
                throw new IllegalStateException("无法连接到存储服务器");
            }
            return new StorageClient1(trackerServer,storageServer);
        }catch (Exception e){
        }
        return null;
    }




}
