package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.INewsBuilder;
import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.rest.resource.NewsRequest;
import com.nationalchip.iot.rest.resource.NewsResponse;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = RestMappingConstant.REST_UPLOAD_ACTION,method= RequestMethod.POST)
    public ResponseEntity<Response> cover(@PathVariable(value = "id")long id, NewsRequest request){
        return super.update(id,request);
    }


    @Override
    @RequestMapping(method = RequestMethod.PATCH,value = RestMappingConstant.REST_ID_MAPPING)
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody NewsRequest request) {
        return super.update(id, request);
    }
}
