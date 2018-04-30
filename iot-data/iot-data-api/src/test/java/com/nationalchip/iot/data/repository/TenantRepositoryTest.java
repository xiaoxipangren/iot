//package com.nationalchip.iot.data.repository;
//
//import com.nationalchip.iot.data.configuration.JpaConfiguration;
//import com.nationalchip.iot.data.model.auth.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.PersistenceContext;
//
//import static org.junit.Assert.*;
//
///**
// * @Author: zhenghq
// * @Description:
// * @Date: 4/24/18 4:56 PM
// * @Modified:
// */
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = JpaConfiguration.class)
//@DataJpaTest
//public class TenantRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private TenantRepository tenantRepository;
//
//    private String username="username";
//    private String password="password";
//    private String email="e@mail.com";
//    private String phone="phone";
//    private User user;
//
//
//    @Before
//    public void setUp() throws Exception {
//        user = newUser();
//        entityManager.persist(user);
//    }
//
//    @Test
//    public void findByUsername() throws Exception {
//        assertEquals(user,tenantRepository.findByUsername(username));
//    }
//
//    @Test
//    public void findByEmail() throws Exception {
//        assertEquals(user,tenantRepository.findByEmail(email));
//    }
//
//    @Transactional
//    @Test
//    public void deleteByUsername() throws Exception {
//        tenantRepository.deleteByUsername(username);
//        assertEquals(true,tenantRepository.count()==0L);
//    }
//
//    @Test
//    public void existsByUsername() throws Exception {
//        assertEquals(true,tenantRepository.existsByUsername(username));
//    }
//
//    @Test
//    public void existsByEmail() throws Exception {
//        assertEquals(true,tenantRepository.existsByEmail(email));
//    }
//
//    @Test
//    public void existsByPhone() throws Exception {
//        assertEquals(true,tenantRepository.existsByPhone(phone));
//    }
//
//    private User newUser(){
//        User user = new User(username,password);
//        user.setEmail(email);
//        user.setPhone(phone);
//
//        return user;
//    }
//
//}