package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IDocumentBuilder;
import com.nationalchip.iot.data.model.IDocument;
import com.nationalchip.iot.rest.controller.DocumentController;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:40 AM
 * @Modified:
 */
@Component
public class DocumentAssembler extends NamedAssembler<IDocument,DocumentResponse,IDocumentBuilder,DocumentRequest> {
    public DocumentAssembler() {
        super(DocumentController.class, DocumentResponse.class);
    }

    @Override
    protected IDocumentBuilder builder() {
        return getBuilderFactory().document();
    }


    @Override
    public DocumentResponse toResource(IDocument entity) {
        DocumentResponse response = super.toResource(entity);
        response.setUrl(entity.getUrl());

        return response;
    }

    @Override
    public IDocumentBuilder fromRequest(DocumentRequest request) {
        IDocumentBuilder builder =  super.fromRequest(request);

        request.getUrl().ifPresent(url -> builder.url(url));

        return builder;
    }
}
