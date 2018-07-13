package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.message.captcha.ICaptcha;
import com.nationalchip.iot.rest.controller.CaptchaController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 7:46 PM
 * @Modified:
 */

@Component
public class CaptchaAssembler extends ResourceAssemblerSupport<ICaptcha,CaptchaResponse> {

    public CaptchaAssembler() {
        super(CaptchaController.class, CaptchaResponse.class);
    }



    @Override
    public CaptchaResponse toResource(ICaptcha entity) {
        CaptchaResponse response = new CaptchaResponse();
        response.setDate(entity.getDate());
        response.setKey(entity.getKey());
        response.setTarget(entity.getTarget());
        response.setAction(entity.getAction());

        if(entity.isImaged()){
            response.add(linkTo(methodOn(CaptchaController.class).get(entity.getKey())).withRel("image"));
        }
        response.add(linkTo(methodOn(CaptchaController.class).get(entity.getKey(),"{code}")).withRel("validate"));
        return response;
    }
}
