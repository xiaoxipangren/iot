package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.INews;

import java.io.InputStream;
import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/14/18 8:49 PM
 * @Modified:
 */
public interface INewsBuilder extends IArchivedBuilder<INews> {

    INewsBuilder content(String content);
    INewsBuilder author(String author);
    INewsBuilder date(Date date);
    INewsBuilder sticky(boolean sticky);
    INewsBuilder abstraction(String abstraction);
    INewsBuilder coverImage(InputStream stream);

}
