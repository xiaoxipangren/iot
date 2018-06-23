//package com.nationalchip.iot.data.manager;
//
//import com.nationalchip.iot.data.resource.auth.Status;
//import com.nationalchip.iot.data.resource.auth.User;
//import com.nationalchip.iot.data.repository.TenantRepository;
//import com.nationalchip.iot.tenancy.ITenantAware;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.when;
//
///**
// * @Author: zhenghq
// * @Description:
// * @Date: 4/25/18 10:34 AM
// * @Modified:
// */
//@RunWith(MockitoJUnitRunner.class)
//public class UserManagerTest {
//
//    private UserManager userManager;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private ITenantAware tenantAware;
//    @Mock
//    private TenantRepository tenantRepository;
//
//    private String username = "username";
//    private String password = "password";
//    private String email = "xxx@abc.com";
//    private String phone="phone";
//    private User testUser;
//
//    @Before
//    public void setUp() throws Exception {
//        userManager = new UserManager();
//        userManager.setRepository(tenantRepository);
//        userManager.setPasswordEncoder(passwordEncoder);
//        userManager.setTenantAware(tenantAware);
//        initUSer();
//
//        when(tenantAware.getCurrentTenant()).thenReturn(username);
//        when(passwordEncoder.encode(anyString())).thenAnswer(a -> a.getArgumentAt(0,String.class));
//        when(tenantRepository.save(any(User.class))).thenAnswer(a ->{
//            User user = a.getArgumentAt(0,User.class);
//            testUser=copyUser(user);
//            return testUser;
//        });
//        when(tenantRepository.existsByPhone(anyString())).thenAnswer(a -> a.getArgumentAt(0,String.class).equals(testUser.getPhone()));
//        when(tenantRepository.existsByEmail(anyString())).thenAnswer(a -> a.getArgumentAt(0,String.class).equals(testUser.getEmail()));
//        when(tenantRepository.existsByUsername(anyString())).thenAnswer(a -> a.getArgumentAt(0,String.class).equals(testUser.getUsername()));
//
//    }
//
//    @Test
//    public void changePassword() throws Exception {
//        String newPwd="newpwd";
//        User user = prepare();
//
//        userManager.changePassword(password,newPwd);
//        assertEquals(false,testUser==user);
//        assertEquals(newPwd,user.getPassword());
//        assertEquals(newPwd,testUser.getPassword());
//    }
//
//    @Test
//    public void resetPassword() throws Exception {
//        User user = prepare();
//        String newPwd="newpwd";
//
//        userManager.resetPassword(username,newPwd);
//        assertEquals(false,testUser==user);
//        assertEquals(newPwd,user.getPassword());
//        assertEquals(newPwd,testUser.getPassword());
//
//    }
//
//    @Test
//    public void changeEmail() throws Exception {
//        User user = prepare();
//
//        String newEmail="newEmail";
//
//        userManager.changeEmail(newEmail);
//
//        assertEquals(false,testUser==user);
//        assertEquals(newEmail,user.getEmail());
//        assertEquals(newEmail,testUser.getEmail());
//
//    }
//
//    @Test
//    public void changePhone() throws Exception {
//        User user = prepare();
//        String newPhone="newphone";
//
//        userManager.changePhone(newPhone);
//
//        assertEquals(false,testUser==user);
//        assertEquals(newPhone,user.getPhone());
//        assertEquals(newPhone,testUser.getPhone());
//
//    }
//
//    @Test
//    public void activeUser() throws Exception {
//        User user = prepare();
//        userManager.activateUser(username);
//
//        assertEquals(false,user==testUser);
//        assertEquals(Status.ACTIVED,user.getStatus());
//        assertEquals(Status.ACTIVED,testUser.getStatus());
//
//    }
//
//    @Test
//    public void userExists() throws Exception {
//        assertEquals(true,userManager.userExists(username));
//        assertEquals(false,userManager.userExists("test"));
//    }
//
//    @Test
//    public void userExistsByPhone() throws Exception {
//        assertEquals(true,userManager.userExistsByPhone(phone));
//        assertEquals(false,userManager.userExistsByPhone("newphone"));
//    }
//
//    @Test
//    public void userExistsByEmail() throws Exception {
//        assertEquals(true,userManager.userExistsByEmail(email));
//        assertEquals(false,userManager.userExistsByEmail("newemail"));
//    }
//
//    @Test(expected = UsernameNotFoundException.class)
//    public void loadUserByUsername() throws Exception {
//        assertEquals(testUser,userManager.loadUserByUsername(username));
//        assertEquals(testUser,userManager.loadUserByUsername(email));
//
//        try{
//            userManager.loadUserByUsername(phone);
//
//        }
//        catch (UsernameNotFoundException ex){
//            System.out.println(ex.getMessage());
//            throw ex;
//        }
//
//
//    }
//
//    @Test
//    public void exists() throws Exception {
//        assertEquals(true,userManager.userExists(username));
//    }
//
//    @Test
//    public void count() throws Exception {
//        when(tenantRepository.count()).thenReturn(2L);
//        assertEquals(2L,userManager.count());
//    }
//
//    @Test
//    public void getFile() throws Exception {
//        when(tenantRepository.findOne(Matchers.anyLong())).thenReturn(testUser);
//        assertEquals(testUser,userManager.getFile(23L));
//    }
//
//    @Test
//    public void create() throws Exception {
//        User user = newUser();
//        when(tenantRepository.save(user)).thenReturn(user);
//        assertEquals(user,userManager.create(user));
//    }
//
//    @Test
//    public void delete() throws Exception {
//
//    }
//
//
//    private User prepare(){
//        initUSer();
//        User user = newUser();
//        when(tenantRepository.findByUsername(username)).thenReturn(user);
//        return user;
//    }
//
//
//    private User copyUser(User user){
//        User u = newUser();
//        u.setUsername(user.getUsername());
//        u.setPassword(user.getPassword());
//        u.setPhone(user.getPhone());
//        u.setEmail(user.getEmail());
//        u.setStatus(user.getStatus());
//        return u;
//    }
//
//    private void initUSer(){
//        testUser = newUser();
//    }
//
//    private User newUser(){
//        User user = new User(username,password);
//        user.setEmail(email);
//        user.setPhone(phone);
//        return user;
//    }
//
//
//}