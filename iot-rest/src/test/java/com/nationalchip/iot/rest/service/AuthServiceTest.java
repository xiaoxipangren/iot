package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.App;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.Admin;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.model.hub.Developer;
import com.nationalchip.iot.rest.model.auth.UserRegister;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/24/18 8:18 PM
 * @Modified:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class AuthServiceTest {

    private String username = "username";
    private String password = "password";
    private String email = "xxx@abc.com";
    private String phone = "phone";


    @Autowired
    private AuthService authService;

    @Autowired
    private UserManager userManager;


    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void register() throws Exception {
        UserRegister userRegister = newUser(0);
        authService.register(userRegister);

        User user = (User) userManager.loadUserByUsername(username);

        assertTrue(user instanceof Developer);
        assertTrue(user.getStatus()== Status.REGISTERED);
        assertTrue(username.equalsIgnoreCase(user.getUsername()));

        userManager.deleteUser(username);
    }

    @Test
    public void registerAdmin() throws Exception {
        UserRegister userRegister = newUser(1);
        authService.register(userRegister);
        User user = (User) userManager.loadUserByUsername(username);

        assertTrue(user instanceof Admin);
        assertTrue(user.getStatus()== Status.REGISTERED);
        assertTrue(username.equalsIgnoreCase(user.getUsername()));

        userManager.deleteUser(username);
    }

    @Test
    public void sendActivateEmail() throws Exception {
    }

    @Test
    public void activate() throws Exception {
    }

    @Test
    public void login() throws Exception {
    }

    @Test
    public void logout() throws Exception {
    }

    @Test
    public void changePassword() throws Exception {
    }

    @Test
    public void sendResetPasswordEmail() throws Exception {
    }

    @Test
    public void resetPassword() throws Exception {
    }

    @Test
    public void changeEmail() throws Exception {
    }

    @Test
    public void usernameExists() throws Exception {
    }

    @Test
    public void phoneExists() throws Exception {
    }

    @Test
    public void emailExists() throws Exception {
    }


    @After
    public void tearDown(){
        userManager.deleteUser(username);
    }

    private UserRegister newUser(int type){
        UserRegister user = new UserRegister();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setType(type);
        return user;
    }
}