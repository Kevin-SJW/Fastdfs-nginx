package com.siemens.fastdfs.service;

import com.siemens.fastdfs.domain.Fastdfs;

import java.util.List;

public interface FastdfsService {

//    /**
//     * 上传
//     * @param local_filename
//     * @return
//     */
//    String upload(String local_filename);

    /**
     * 下载
     * @param image_path
     * @return
     */
    Byte download(String image_path);

//    /**
//     * 保存
//     * @param fastdfs
//     * @return
//     */
//    Fastdfs save(Fastdfs fastdfs);
//
//    /**
//     * 获取数据库信息
//     * @return
//     */
//
//    List<Fastdfs> getDbInfo();
//
//
//    /**
//     * 保存path
//     * @return
//     */
//    String savePath(Fastdfs fastdfs);

    /**
     * 获取文件信息
     * @return
     */
    String getFileInfo(String image_path);

//    /**
//     * 删除db
//     * @param id
//     */
//    void delete(Long id);

    /**
     * 删除server
     * @param image_path
     */
    void delete(String image_path);


}
