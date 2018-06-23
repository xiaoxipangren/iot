package com.nationalchip.iot.common.io;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/1/18 9:35 AM
 * @Modified:
 */
public interface IFileRepository {

    String store(InputStream stream,String filename,String ...paths);
    File get(String filename,String ... paths);
    void delete(String filename,String ... paths);
    boolean exists(String filename,String... paths);

}
