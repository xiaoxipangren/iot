package com.nationalchip.iot.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nationalchip.iot.App;
import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.rest.resource.auth.LoginUser;
import com.nationalchip.iot.rest.resource.auth.PwdReset;
import com.nationalchip.iot.rest.resource.auth.RegisterUser;
import com.nationalchip.iot.rest.resource.auth.SendMail;
import com.nationalchip.iot.security.configuration.RestConstant;
import com.nationalchip.iot.security.configuration.RestSecurityProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 7:58 PM
 * @Modified:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestSecurityProperty restSecurityProperty;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();


    private String username = "admin";
    private String password = "zhqadmin";
    private String email = "zhenghq@nationalchip.com";
    private String phone = "17681803790";
    private static final String ACTION_VALIDATE="validate";
    private static final String ACTION_RESETPWD="resetpwd";

    private String baseMapping= RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING;



    @Test
    public void register() throws Exception {
        RegisterUser user = new RegisterUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCode("twte");

        String response = post(RestConstant.REST_REGISTER_ACTION,user);
        System.out.println(response);


    }

    @Test
    public void validateCode() throws Exception {

    }

    @Test
    public void sendMail() throws Exception {
        SendMail mail = new SendMail();
        mail.setEmail(email);
        mail.setAction(ACTION_VALIDATE);

        String response = post(RestConstant.REST_SENDMAIL_ACTION,mail);
        System.out.println(response);

    }

    @Test
    public void login() throws Exception {



        LoginUser user  = new LoginUser();
        user.setUsername(username);
        user.setPassword("password");

        String response = post(RestConstant.REST_LOGIN_ACTION,user);
        System.out.println(response);

    }

    @Test
    public void logout() throws Exception {
    }

    @Test
    public void exists() throws Exception {
    }

    @Test
    public void changePassword() throws Exception {

    }

    @Test
    public void resetPassword() throws Exception {
        SendMail mail = new SendMail();
        mail.setEmail(email);
        mail.setAction(ACTION_RESETPWD);
        post(RestConstant.REST_SENDMAIL_ACTION,mail);

        String newpwd="password";

        PwdReset reset = new PwdReset();
        reset.setEmail(email);
        reset.setCode((String)redisTemplate.opsForValue().get(KeyHelper.resetPasswordKey(email)));
        reset.setPassword(newpwd);

        String respons = post(RestConstant.REST_RESETPWD_ACTION,reset);

        System.out.println(respons);

    }

    @Test
    public void changeEmail() throws Exception {
    }

    private <T> T decode(String json,Class<T> tClass){
        ObjectReader or = mapper.readerFor(tClass);
        try {
            return or.readValue(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String encode(Object obj){
        try {
            return ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String url(String url){
        return baseMapping+url;
    }

    private String post(String url,Object data){

        String requestJson = encode(data);

        ResultActions actions = null;
        try {
            actions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url(url))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        MvcResult result = actions.andReturn();

        try {
            String response = result.getResponse().getContentAsString();
            return  response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}