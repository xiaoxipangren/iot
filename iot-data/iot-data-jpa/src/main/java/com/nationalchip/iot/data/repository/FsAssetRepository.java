package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.common.io.IFileRepository;
import com.nationalchip.iot.data.configuration.DataProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 1:38 PM
 * @Modified:
 */

@Component
public class FsAssetRepository implements IFsRepository {

    @AutoLogger
    private Logger logger;

    private final static String BASE_PATH="asset";

    @Qualifier("hashFileRepository")
    @Autowired
    private IFileRepository hashFileRepository;

    @Autowired
    private DataProperties dataProperties;


    @Override
    public String create(InputStream content, String sha1) {

        return hashFileRepository.store(content,sha1,dataProperties.getFs().getRepo(),BASE_PATH);
    }



    @Override
    public void deleteBySha1(String sha1) {
        hashFileRepository.delete(sha1,dataProperties.getFs().getRepo(),BASE_PATH);
    }

    @Override
    public boolean existsBySha1(String sha1) {
        return hashFileRepository.exists(sha1,dataProperties.getFs().getRepo(),BASE_PATH);
    }

    @Override
    public File getFile(String sha1) {
        return hashFileRepository.get(sha1,dataProperties.getFs().getRepo(),BASE_PATH);
    }
}
