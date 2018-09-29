package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IAssetBuilder;
import com.nationalchip.iot.data.manager.AssetManager;
import com.nationalchip.iot.data.model.IAsset;
import com.nationalchip.iot.rest.resource.AssetRequest;
import com.nationalchip.iot.rest.resource.AssetResponse;
import com.nationalchip.iot.rest.resource.Response;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import com.nationalchip.iot.tenancy.ITenantAware;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import static com.nationalchip.iot.rest.resource.Response.ok;
import static com.nationalchip.iot.security.authority.AuthorityExpression.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:45 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = REST_BASE_MAPPING+ REST_ASSET_MAPPING)
@Api(tags = "下载资源API")
public class AssetController extends FiledController<IAsset,AssetResponse,IAssetBuilder,AssetRequest> {

    @Autowired
    private ITenantAware tenantAware;


    @Override
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(HAS_AUTH_CREATE_ASSET)
    public ResponseEntity<Response> create(AssetRequest request) {
        return super.create(request);
    }



    @RequestMapping(method = RequestMethod.GET,value = "/tags")
    public ResponseEntity<Response> getTags() {

        return ok(getManager().getTags());
    }


    @Override
    public ResponseEntity<Response> getAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                           @RequestParam(value = "pagesize",defaultValue = "10")int pagesize,
                                           @RequestParam(value= "filter",defaultValue = "")String filter,
                                           @RequestParam(value="sort",defaultValue="")String sort) {

        filter = filter.trim();

        //#TODO:
        //匿名登录时如果手动指定了shared=false,需要改为shared=true
        if (tenantAware.isAnonymous()){

            filter = filter + (filter.isEmpty()?"":",") +  "shared==true";
        }



        return super.getAll(page,pagesize,filter,sort);
    }

    @RequestMapping(value =REST_ID_MAPPING,method = RequestMethod.PATCH)
    @Override
    @PreAuthorize(HAS_AUTH_UPDATE_ASSET)
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody AssetRequest request) {
        return super.update(id, request);
    }


    @Override
    @RequestMapping(method = RequestMethod.DELETE,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_DELETE_ASSET)
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @PreAuthorize(HAS_AUTH_UPDATE_ASSET)
    @RequestMapping(value = REST_UPLOAD_ACTION,method= RequestMethod.POST)
    public ResponseEntity<Response> upload(@PathVariable(value = "id")long id, AssetRequest request) {
        return super.upload(id, request);
    }

    @Override
    public AssetManager getManager() {
        return (AssetManager) super.getManager();
    }
}
