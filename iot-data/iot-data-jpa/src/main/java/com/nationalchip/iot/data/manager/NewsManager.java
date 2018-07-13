package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.common.io.IFileRepository;
import com.nationalchip.iot.common.io.IOHelper;
import com.nationalchip.iot.data.configuration.DataProperty;
import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.data.model.News;
import com.nationalchip.iot.data.repository.NewsRepository;
import com.nationalchip.iot.helper.RegexHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 9:21 AM
 * @Modified:
 */

@Component
public class NewsManager extends ArchivedManager<INews,News> implements INewsManager {

    private static final String BASE64_PREFIX="data:image";

    @Autowired
    private DataProperty dataProperty;

    @Qualifier("hashFileRepository")
    @Autowired
    private IFileRepository hashFileRepository;

    @Override
    protected void preCreate(INews iNews) {
        super.preCreate(iNews);

        saveImages(iNews);

    }


    @Override
    protected void postUpdate(INews iNews) {
        super.postUpdate(iNews);

        saveImages(iNews);

    }


    private void saveImages(INews iNews){
        News news = (News) iNews;

        saveCover(news);
        String content = news.getContent();
        news.setContent(saveImages(content));
    }


    private void saveCover(News news){
        if(news.getCoverImage()!=null){
            String cover = hashFileRepository.store(news.getCoverImage(),null, dataProperty.getFs().getRepo(), dataProperty.getFs().getImage(), dataProperty.getFs().getNews());
            news.setCover(cover);
        }

    }


    private String saveImages(String content){
        Iterable<String> images = RegexHelper.extract(content,"\"data:image/(\\S+)\"");
        for(String image : images){
            String path = saveImage(image);
            content = content.replace(image,path);
        }


        return content;

    }

    private String saveImage(String base64){
        byte[] bytes = java.util.Base64.getMimeDecoder().decode(base64.split("base64,")[1].getBytes(Charset.forName("utf-8")));

        InputStream stream = new ByteArrayInputStream(bytes);

        String hash = hashFileRepository.store(stream,null, dataProperty.getFs().getRepo(), dataProperty.getFs().getImage(), dataProperty.getFs().getNews());

        return Paths.get(dataProperty.getFs().getNews(), IOHelper.hashPath(hash),hash).toString();

    }

    @Override
    public NewsRepository getRepository() {
        return (NewsRepository) super.getRepository();
    }

    @Override
    public Iterable<INews> findTricky() {
        return convert(getRepository().findByStickyOrderByDateDesc(true));
    }
}
