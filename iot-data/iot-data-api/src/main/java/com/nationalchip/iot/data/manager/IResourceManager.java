package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IResource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:33 PM
 * @Modified:
 */
public interface IResourceManager {

    boolean existsBySha1(String sha1);
    IResource findBySha1(String sha1);
    void deleteBySha1(String sha1);

}
