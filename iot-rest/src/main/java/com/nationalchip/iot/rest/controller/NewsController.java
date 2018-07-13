package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.INewsBuilder;
import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.rest.resource.NewsRequest;
import com.nationalchip.iot.rest.resource.NewsResponse;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 2:14 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = RestMappingConstant.REST_BASE_MAPPING+ RestMappingConstant.REST_NEWS_MAPPING)
public class NewsController extends BaseController<INews,NewsResponse,INewsBuilder,NewsRequest> {
    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> create(NewsRequest request) {
        return super.create(request);
    }
}
