package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.configuration.RestConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/23/18 11:20 AM
 * @Modified:
 */
public interface IController<T extends IEntity> {

    @RequestMapping(value = RestConstant.REST_ID_MAPPING,method= RequestMethod.GET)
    ResponseEntity<Response> get(@PathVariable("id")final Long id);

    @RequestMapping(method= RequestMethod.GET)
    ResponseEntity<Response> getAll(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "pagesize",defaultValue = "10")int pagesize);

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity<Response> delete(@PathVariable("id")final Long id);

}
