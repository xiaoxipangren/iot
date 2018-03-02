package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.rest.model.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 5:15 PM
 * @Modified:
 */

@RestController
@RequestMapping(value = "/api/v1/echo")
public class EchoController {

    @RequestMapping(value = "/echo/{content}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> echo(@PathVariable String content){

        return new ResponseEntity<String>(content,HttpStatus.OK);
    }


    @RequestMapping(value = "/modify/{content}")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<String> modify(@PathVariable String content){

        return new ResponseEntity<String>(content+System.currentTimeMillis(),HttpStatus.OK);
    }
}


