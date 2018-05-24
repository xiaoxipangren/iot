package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IResource;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 2:41 PM
 * @Modified:
 */
public interface IResourceRepository {

    IResource create(InputStream content,String sha1);

    void delete(String sha1);

    File get(String sha1);
}
