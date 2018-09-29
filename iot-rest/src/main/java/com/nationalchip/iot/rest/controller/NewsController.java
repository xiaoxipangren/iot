package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.INewsBuilder;
import com.nationalchip.iot.data.model.INews;
import com.nationalchip.iot.rest.resource.NewsRequest;
import com.nationalchip.iot.rest.resource.NewsResponse;
import com.nationalchip.iot.rest.resource.Response;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.nationalchip.iot.security.authority.AuthorityExpression.*;


/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 2:14 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = REST_BASE_MAPPING+ REST_NEWS_MAPPING)
public class NewsController extends BaseController<INews,NewsResponse,INewsBuilder,NewsRequest> {
    @Override
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(HAS_AUTH_CREATE_NEWS)
    public ResponseEntity<Response> create(NewsRequest request) {
        return super.create(request);
    }

    @RequestMapping(value = REST_UPLOAD_ACTION,method= RequestMethod.POST)
    @PreAuthorize(HAS_AUTH_UPDATE_NEWS)
    public ResponseEntity<Response> cover(@PathVariable(value = "id")long id, NewsRequest request){
        return super.update(id,request);
    }


    @Override
    @RequestMapping(method = RequestMethod.PATCH,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_UPDATE_NEWS)
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody NewsRequest request) {
        return super.update(id, request);
    }


    @Override
    @RequestMapping(method = RequestMethod.DELETE,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_DELETE_NEWS)
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return super.delete(id);
    }
}
