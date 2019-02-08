package com.siemens.fastdfs.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 监听
 * @CreatedBy
 * @LastModifiedBy
 * 自动注入用户名
 */
@Configuration
public class UserAuditorAware implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
//        TODO: 根据实际情况取真实用户
        return Optional.of("admin");
    }
}


