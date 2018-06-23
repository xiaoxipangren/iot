package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/14/18 3:16 PM
 * @Modified:
 */

import java.io.InputStream;
import java.util.Date;

/**
 * 新闻
 */

public interface INews extends IArchivedEntity{
    Date getDate();
    String getAuthor();
    String getContent();
    String getCover();
    boolean isSticky();
    String getAbstraction();
    InputStream getCoverImage();
}
