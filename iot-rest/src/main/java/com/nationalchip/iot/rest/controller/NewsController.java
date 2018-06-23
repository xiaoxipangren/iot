//package com.nationalchip.iot.rest.controller;
//
//import com.nationalchip.iot.common.network.IPAware;
//import com.nationalchip.iot.data.builder.INewsBuilder;
//import com.nationalchip.iot.data.manager.NewsManager;
//import com.nationalchip.iot.data.model.INews;
//import com.nationalchip.iot.rest.exception.NewsException;
//import com.nationalchip.iot.rest.resource.Response;
//import com.nationalchip.iot.security.configuration.RestConstant;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Date;
//
///**
// * @Author: zhenghq
// * @Description:
// * @Date: 6/15/18 2:14 PM
// * @Modified:
// */
//@RestController
//@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_NEWS_MAPPING)
//public class NewsController extends BaseController {
//
//    @Autowired
//    private NewsManager newsManager;
//
//    @Autowired
//    private IPAware ipAware;
//
//
//    @RequestMapping(value = "/post",method= RequestMethod.POST)
//    public ResponseEntity<Response> post(@RequestParam(value = "cover") MultipartFile image,
//                                             @RequestParam(value = "title") String title,
//                                             @RequestParam(value = "content") String content,
//                                             @RequestParam(value = "author",required = false) String author,
//                                             @RequestParam(value = "abstract",required = false) String abstraction,
//                                             @RequestParam(value = "date",required = false)Date date,
//                                             @RequestParam(value="tag",required = false)String tag,
//                                             @RequestParam(value = "sticky",required = false) boolean sticky){
//
//        try {
//
//            System.out.println(ipAware.getHost()+":"+ipAware.getPort());
//
//            INewsBuilder builder = getBuilderFactory().news();
//            builder.abstraction(abstraction)
//                    .author(author)
//                    .content(content)
//                    .coverImage(image.getInputStream())
//                    .sticky(sticky)
//                    .date(date==null? new Date():date)
//                    .tag(tag)
//                    .name(title);
//
//
//            newsManager.create(builder);
//
//            return ok("新闻发布成功");
//        } catch (IOException e) {
//            throw new NewsException("新闻发布失败："+e.getMessage());
//        }
//
//    }
//
//}
