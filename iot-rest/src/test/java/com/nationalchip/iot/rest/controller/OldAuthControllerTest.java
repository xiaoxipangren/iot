package com.nationalchip.iot.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nationalchip.iot.App;
import com.nationalchip.iot.rest.resource.auth.LoginUser;
import com.nationalchip.iot.rest.resource.auth.UserInfo;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import com.nationalchip.iot.security.configuration.RestSecurityProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/19/18 4:26 PM
 * @Modified:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OldAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestSecurityProperty restSecurityProperty;

    private String username = "username";
    private String password = "password";
    private String email = "xxx@abc.com";
    private String phone = "phone";

    private String baseMapping= RestMappingConstant.REST_BASE_MAPPING+ RestMappingConstant.REST_AUTH_MAPPING;


    @Before
    public void setUp() throws Exception {

    }

    @Test

    public void test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        LoginUser register = newUser(0);
        String requestJson = ow.writeValueAsString(register);

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post(baseMapping+ RestMappingConstant.REST_REGISTER_ACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        );

        MvcResult result = actions.andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);


        LoginUser login = new LoginUser();
        login.setUsername(username);
        login.setPassword(password);


        requestJson = ow.writeValueAsString(login);


        actions = mockMvc.perform(MockMvcRequestBuilders
                .post(baseMapping+ RestMappingConstant.REST_LOGIN_ACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        );

        result = actions.andReturn();

        response = result.getResponse().getContentAsString();
        System.out.println(response);



        ObjectReader or = mapper.readerFor(UserInfo.class);
        UserInfo userInfo = or.readValue(response);
        String token = userInfo.getToken();





        actions = mockMvc.perform(MockMvcRequestBuilders
                .get(baseMapping+"/logout")
                .header(restSecurityProperty.getJwt().getHeader(), restSecurityProperty.getJwt().getPrefix()+" "+token)

        );
        result = actions.andReturn();
        response = result.getResponse().getContentAsString();
        System.out.println(response);


    }

    private LoginUser newUser(int type){
        LoginUser user = new LoginUser();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }




}