package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.INewsBuilder;
import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.rest.controller.NewsController;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/28/18 2:17 PM
 * @Modified:
 */

@Component
public class NewsAssembler extends ArchivedAssembler<INews,NewsResponse,INewsBuilder,NewsRequest>{


    public NewsAssembler() {
        super(NewsController.class, NewsResponse.class);
    }

    @Override
    protected INewsBuilder builder() {
        return getBuilderFactory().news();
    }


    @Override
    public NewsResponse toResource(INews entity) {
        NewsResponse  response = super.toResource(entity);
        response.setAbstraction(entity.getAbstraction());
        response.setAuthor(entity.getAuthor());
        response.setContent(entity.getContent());
        response.setCover(entity.getCover());
        response.setDate(entity.getDate());
        response.setSticky(entity.isSticky());

        return response;
    }

    @Override
    public INewsBuilder fromRequest(NewsRequest request) {
        INewsBuilder builder = super.fromRequest(request);
        request.getAbstraction().ifPresent(a -> builder.abstraction(a));
        request.getAuthor().ifPresent(a -> builder.author(a));
        request.getContent().ifPresent(c -> builder.content(c));
        request.getCover().ifPresent(c -> {
            try {
                builder.coverImage(c.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        request.isSticky().ifPresent(s -> builder.sticky(s));
        request.getDate().ifPresent(d -> builder.date(d));

        return builder;
    }
}
