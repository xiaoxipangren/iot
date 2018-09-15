package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.Document;
import com.nationalchip.iot.data.model.IDocument;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:21 AM
 * @Modified:
 */
public class DocumentBuilder extends NamedCreupdate<IDocumentBuilder,IDocument> implements IDocumentBuilder {

    private String url;


    @Override
    protected IDocument newInstance() {
        return new Document();
    }

    @Override
    protected void apply(IDocument entity) {
        super.apply(entity);
        this.<Document>tryCast(entity).ifPresent(
                d -> {
                    if(url!=null)
                        d.setUrl(url);
                }
        );
    }

    @Override
    public IDocumentBuilder url(String url) {
        this.url=url;
        return self();
    }
}
