package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IDocument;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:17 AM
 * @Modified:
 */
public interface IDocumentBuilder extends INamedBuilder<IDocument>{
    IDocumentBuilder url(String url);
}
