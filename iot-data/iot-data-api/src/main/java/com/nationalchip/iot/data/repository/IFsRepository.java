package com.nationalchip.iot.data.repository;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 2:41 PM
 * @Modified:
 */
public interface IFsRepository {

    String create(InputStream content,String sha1);

    void deleteBySha1(String sha1);

    boolean existsBySha1(String sha1);

    File getFile(String sha1);
}
