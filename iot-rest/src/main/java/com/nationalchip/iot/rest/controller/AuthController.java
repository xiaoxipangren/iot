package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.Admin;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.model.hub.Developer;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.model.RestResult;
import com.nationalchip.iot.rest.model.auth.*;
import com.nationalchip.iot.rest.service.MailService;
import com.nationalchip.iot.security.configuration.RestConstant;
import com.nationalchip.iot.rest.configuration.RestProperty;
import com.nationalchip.iot.rest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING)
public class AuthController {

    private final static String USER_NAME="username";
    private final static String USER_PHONE="phone";
    private final static String USER_EMAIL="email";
    private static final String ACTIVATE_EMAIL_TITLE="国芯开发者账号激活";



    @Autowired
    private RestProperty restProperty;
    @Autowired
    private ISecurityContext systemSecurityContext;
    @Autowired
    private UserManager userManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${iot.rest.activation.expiration}")
    private int validationExpiration;


    @RequestMapping(value = RestConstant.REST_REGISTER_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<RestResult> register(@RequestBody UserRegister user){

        boolean result = registerUser(user);

        return ok("注册成功");
    }

    @RequestMapping(value = RestConstant.REST_SENDMAIL_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<Boolean> sendMail(@RequestBody UserMail user){

        boolean result = sendActivateEmail(user.getUrl(),user.getUsername());

        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @RequestMapping(value = RestConstant.REST_ACTIVATE_ACTION)
    public ResponseEntity<Boolean> activate(@RequestParam String username,@RequestParam String code){

        boolean result = activateUser(username,code);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }



    @RequestMapping(value = RestConstant.REST_LOGIN_ACTION,method= RequestMethod.POST)
    public ResponseEntity<UserInfo> login(@RequestBody UserLogin login){

        UserInfo user = authService.login(login.getUsername(),login.getPassword());

        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/logout")
    public ResponseEntity<Boolean> logout(@RequestHeader(RestConstant.REST_JWT_HEADER) String token ){

        if(token==null){
            return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
        }

        token = token.replace(RestConstant.REST_JWT_PREFIX,"");

        boolean result = authService.logout(token);

        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }


    public ResponseEn



    @RequestMapping(value = RestConstant.REST_EXISTS_ACTION+"/{prop}/{value:.+}")
    public ResponseEntity<UserExists> exists(@PathVariable String prop, @PathVariable String value){
        boolean result = false;
        if(prop.toLowerCase().equals(USER_NAME)){
            result = authService.usernameExists(value);
        }
        else if(prop.toLowerCase().equals(USER_EMAIL)){
            result = authService.emailExists(value);
        }
        else if(prop.toLowerCase().equals(USER_PHONE)){
            result = authService.phoneExists(value);
        }
        else{
            return new ResponseEntity<UserExists>(new UserExists(false),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<UserExists>(new UserExists(result),HttpStatus.OK);
    }



    private boolean registerUser(UserRegister user){
        try{
            systemSecurityContext.runAsSystem(()->{

                User u=null;
                int type = user.getType();
                switch (type){
                    case 0:
                        u = new Developer(user.getUsername(),user.getPassword());
                        break;
                    case 1:
                        u = new Admin(user.getUsername(),user.getPassword());
                        break;
                    default:
                        u = new Developer(user.getUsername(),user.getPassword());

                }
                u.setEmail(user.getEmail());
                u.setPhone(user.getPhone());
                u.setStatus(Status.REGISTERED);

                userManager.createUser(u);
                return null;
            });

            return true;
        }
        catch (Exception e){
            throw new AuthException(e.getMessage());
        }
    }


    private boolean sendActivateEmail(String url,String username){

        User user=null;
        try{
            user =(User) userManager.loadUserByUsername(username);
        }catch (Exception e){
            throw new AuthException(e.getMessage());
        }

        Status status =user.getStatus();
        if(status==Status.REGISTERED){
            String content = generateActivateContent(user.getUsername(),url);
            mailService.sendSimple(user.getEmail(),ACTIVATE_EMAIL_TITLE,content,true);
            return true;
        }
        else{
            return false;
        }

    }

    private String generateActivateContent(String username,String url){
        String key = KeyHelper.activateAccountKey(username);
        String code = generateCacheCode(key);
        url = generateClickUrl(url,username,code);
        return String.format("尊敬的国芯开发者%s<br>你好！<br>请<a href='%s'>点此</a>以激活账号，或复制如下链接到浏览器中打开：<br>%s。",username,url,url);

    }

    private String generateCacheCode(String key){
        String code = UUID.randomUUID().toString().replace("-","").toLowerCase();
        redisTemplate.opsForValue().set(key,code,validationExpiration, TimeUnit.SECONDS);
        return code;
    }

    private String generateClickUrl(String url, String username, String code){
        StringBuilder sb = new StringBuilder(url);
        return sb.append(String.format("?username=%s&code=%s",username,code))
                .toString();
    }

    private boolean activateUser(String username,String code){

        String key = KeyHelper.activateAccountKey(username);

        if(redisTemplate.hasKey(key)){
            String cacheCode = (String)redisTemplate.opsForValue().get(key);
            if(cacheCode!=null && cacheCode.equals(code)){
                try{
                    userManager.activeUser(username);
                }
                catch (Exception e){
                    throw new AuthException("激活失败");
                }
                return true;
            }
            else{
                throw new AuthException(String.format("激活码错误"),HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new AuthException(String.format("激活码已过期"),HttpStatus.BAD_REQUEST);
        }

    }


    private ResponseEntity<RestResult> ok(String message){
        return new ResponseEntity<RestResult>(new RestResult(message),HttpStatus.OK);
    }

}
