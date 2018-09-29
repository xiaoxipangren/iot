package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.rest.resource.IRequest;
import com.nationalchip.iot.rest.resource.Response;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/23/18 11:20 AM
 * @Modified:
 */
public interface IController<T extends IEntity,Q extends IRequest> {

    @RequestMapping(value = REST_ID_MAPPING,method= RequestMethod.GET)
    ResponseEntity<Response> get(@PathVariable("id")final Long id);

    @RequestMapping(method= RequestMethod.GET)
    ResponseEntity<Response> getAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                    @RequestParam(value = "pagesize",defaultValue = "10")int pagesize,
                                    @RequestParam(value = "sql",defaultValue = "")String sql,
                                    @RequestParam(value="sort",defaultValue="")String sort);

    @RequestMapping(value = REST_ID_MAPPING,method= RequestMethod.HEAD)
    ResponseEntity<Response> exists(@PathVariable("id")final Long id);

    @RequestMapping(method= RequestMethod.HEAD)
    ResponseEntity<Response> count(@RequestParam(value = "sql",defaultValue = "")String sql);



    @RequestMapping(method = RequestMethod.DELETE,value = REST_ID_MAPPING)
    ResponseEntity<Response> delete(@PathVariable("id")final Long id);

    @RequestMapping(value = REST_ID_MAPPING,method= RequestMethod.PATCH)
    ResponseEntity<Response> update(@PathVariable("id")final Long id, @RequestBody final Q request);

    @RequestMapping(method= RequestMethod.POST)
    ResponseEntity<Response> create(@RequestBody final Q request);

}
