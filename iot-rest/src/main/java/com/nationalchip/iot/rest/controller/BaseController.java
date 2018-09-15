package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.IBuilderFactory;
import com.nationalchip.iot.data.manager.IManager;
import com.nationalchip.iot.data.model.IEntity;
import com.nationalchip.iot.data.specification.SortParser;
import com.nationalchip.iot.rest.configuration.RestProperty;
import com.nationalchip.iot.rest.exception.ResourceNotFoundException;
import com.nationalchip.iot.rest.resource.*;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 2:32 PM
 * @Modified:
 */
public abstract class BaseController<T extends IEntity,R extends BaseResponse,B extends IBuilder<T>,Q extends BaseRequest> implements IController<T,Q> {


    private final static int MAX_PAGESIZE=20;
    private final static int DEFAULT_PAGESIZE=10;

    @Autowired
    private SortParser sortParser;

    @Autowired
    private RestProperty restProperty;

    @Autowired
    private IManager<T> manager;

    public IAssembler<T, R, B, Q> getAssembler() {
        return assembler;
    }

    public void setAssembler(IAssembler<T, R, B, Q> assembler) {
        this.assembler = assembler;
    }

    @Autowired
    private IAssembler<T,R,B,Q> assembler;

    public IManager<T> getManager() {
        return manager;
    }

    public void setManager(IManager<T> manager) {
        this.manager = manager;
    }

    //@PathVariable等注解不会继承

    @Override
    public ResponseEntity<Response> get(@PathVariable Long id) {

        try{
            T t = manager.findOne(id);

            return Response.ok(assembler.toResource(t));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException();
        }


    }

    @Override
    public ResponseEntity<Response> getAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                           @RequestParam(value = "pagesize",defaultValue = "10")int pagesize,
                                           @RequestParam(value= "filter",defaultValue = "")String filter,
                                           @RequestParam(value="sort",defaultValue="")String sort) {
        page = validatePage(page-1);//调整page从1开始计数
        pagesize = validatePagesize(pagesize);
        Pageable pageable = new PageRequest(page,pagesize,sortParser.parse(sort));

        Page<T> result = null;
        if(filter !=null && !filter.isEmpty())
            result = manager.findAll(pageable,filter);
        else{
            result = manager.findAll(pageable);
        }

        return Response.ok(toPage(result,page,pagesize));
    }

    @Override
    public ResponseEntity<Response> exists(Long id) {
        boolean existed = manager.exists(id);

        return Response.ok(restProperty.getExistedHeader(),String.valueOf(existed));
    }

    @Override
    public ResponseEntity<Response> count( @RequestParam(value= "filter",defaultValue = "")String filter) {
        long count = manager.count(filter);
        return Response.ok(restProperty.getCountHeader(),String.valueOf(count));
    }



    @Override
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        manager.delete(id);
        return Response.deleted();
    }


    @Override
    @RequestMapping(value = RestMappingConstant.REST_ID_MAPPING,method= RequestMethod.PATCH)
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody final Q request) {

        B builder = getAssembler().fromRequest(request);
        builder.id(id);

        T t = getManager().update(builder);

        return Response.ok("更新成功",getAssembler().toResource(t));
    }

    @Override
    public ResponseEntity<Response> create(@RequestBody final Q request) {

        B builder = getAssembler().fromRequest(request);

        T t = getManager().create(builder);

        return Response.created(getAssembler().toResource(t));
    }


    private int validatePagesize(int pagesize){
        if(pagesize<1)
            return DEFAULT_PAGESIZE;
        if(pagesize>MAX_PAGESIZE)
            return MAX_PAGESIZE;

        return pagesize;
    }

    private int validatePage(int page){
        return page<0?0:page;
    }

    protected PageResponse<R> toPage(Page<T> result,int page,int pageSize){

        page = (page>result.getTotalPages())?result.getTotalPages():page;
        int offset =page*pageSize;

        return new PageResponse<>(((BaseAssembler<T,R,B,Q>)assembler).toResources(result.getContent()),result.getTotalElements(),offset);

    }
}
