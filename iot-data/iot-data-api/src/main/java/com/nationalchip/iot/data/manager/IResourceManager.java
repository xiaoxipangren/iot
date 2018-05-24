package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IResource;
import org.springframework.data.domain.Page;

import java.io.File;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:33 PM
 * @Modified:
 */
public interface IResourceManager extends IFiledManager<IResource> {

    Iterable<IResource> findByCategory(String catetory);

}
