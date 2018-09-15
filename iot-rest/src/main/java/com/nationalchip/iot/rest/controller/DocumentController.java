package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IDocumentBuilder;
import com.nationalchip.iot.data.manager.IDocumentManager;
import com.nationalchip.iot.data.model.IDocument;
import com.nationalchip.iot.rest.resource.DocumentRequest;
import com.nationalchip.iot.rest.resource.DocumentResponse;
import com.nationalchip.iot.rest.resource.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nationalchip.iot.security.configuration.RestMappingConstant.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:46 AM
 * @Modified:
 */
@RestController
@RequestMapping(value = REST_BASE_MAPPING+REST_DOCUMENT_MAPPING)
public class DocumentController extends BaseController<IDocument,DocumentResponse,IDocumentBuilder,DocumentRequest> {
    @Override
    public ResponseEntity<Response> getAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                           @RequestParam(value = "pagesize",defaultValue = "10")int pagesize,
                                           @RequestParam(value= "filter",defaultValue = "")String filter,
                                           @RequestParam(value="sort",defaultValue="")String sort) {

        return super.getAll(page, pagesize, filter, sort);
    }

    @Override
    public IDocumentManager getManager() {
        return (IDocumentManager) super.getManager();
    }


    @Override
    @RequestMapping(method = RequestMethod.PATCH,value = REST_ID_MAPPING )
    public ResponseEntity<Response> update(@PathVariable Long id,@RequestBody DocumentRequest request) {
        return super.update(id, request);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> create(@RequestBody DocumentRequest request) {
        return super.create(request);
    }
}
