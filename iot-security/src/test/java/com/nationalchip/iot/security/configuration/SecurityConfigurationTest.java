package com.nationalchip.iot.security.configuration;

import com.nationalchip.iot.data.configuration.JpaConfiguration;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/27/18 4:56 PM
 * @Modified:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SecurityConfigurationTest extends TestCase {


    private PasswordEncoder passwordEncoder =new  BCryptPasswordEncoder();

    @Test
    public void testPasswordEncoder() throws Exception {
        String pw="zhqadmin";
        String hash = passwordEncoder.encode(pw);
        Assert.assertTrue(passwordEncoder.matches(pw,hash));
    }

}