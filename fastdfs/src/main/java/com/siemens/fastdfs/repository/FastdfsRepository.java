package com.siemens.fastdfs.repository;

import com.siemens.fastdfs.domain.Fastdfs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastdfsRepository extends JpaRepository<Fastdfs, Long> {

//    String savePath(Fastdfs fastdfs);

}
