package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.data.model.News;

import java.io.InputStream;
import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/14/18 9:32 PM
 * @Modified:
 */
public class NewsBuilder extends ArchivedCreupdate<INewsBuilder,INews> implements INewsBuilder {

    private String content;
    private String author;
    private Date date;
    private boolean sticky;
    private String abstraction;
    private InputStream stream;


    @Override
    protected INews newInstance() {
        return new News();
    }

    @Override
    public INewsBuilder content(String content) {
        this.content=content;
        return self();
    }

    @Override
    public INewsBuilder author(String author) {
        this.author=author;
        return self();
    }

    @Override
    public INewsBuilder date(Date date) {
        this.date=date;
        return self();
    }


    @Override
    public INewsBuilder sticky(boolean sticky) {
        this.sticky=sticky;
        return self();
    }

    @Override
    public INewsBuilder abstraction(String abstraction) {
        this.abstraction=abstraction;
        return self();
    }

    @Override
    public INewsBuilder coverImage(InputStream stream) {
        this.stream=stream;
        return self();
    }

    @Override
    protected void apply(INews entity) {
        super.apply(entity);
        this.<News>tryCast(entity).ifPresent(
                news ->{

                    if(content!=null)
                        news.setContent(content);

                    if(date!=null)
                        news.setDate(date);

                    if(sticky != news.isSticky())
                        news.setSticky(sticky);

                    if(author!=null)
                        news.setAuthor(author);

                    if(abstraction!=null)
                        news.setAbstraction(abstraction);

                    if(stream!=null)
                        news.setCoverImage(stream);

                }
        );
    }
}
