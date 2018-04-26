package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.Admin;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.model.hub.Developer;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.exception.RestException;
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
public class AuthController extends BaseController{

    private final static String USER_NAME="username";
    private final static String USER_PHONE="phone";
    private final static String USER_EMAIL="email";
    private static final String ACTIVATE_EMAIL_TITLE="国芯开发者账号激活";
    private static final String ACTION_ACTIVATE="activate";
    private static final String ACTION_RESETPWD="resetpwd";

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
    public ResponseEntity<RestResult> register(@RequestBody InputUser user){

        boolean result = registerUser(user);

        return ok("注册成功");
    }

    @RequestMapping(value = RestConstant.REST_SENDMAIL_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<RestResult> sendMail(@RequestBody InputUser user){

        if(user.getSetMailAction().equals(ACTION_ACTIVATE)){
            sendActivateEmail(user.getUrl(),user.getUsername());
        }
        else if(user.getSetMailAction().equals(ACTION_RESETPWD)){
            sendResetPasswordEmail(user.getUrl(),user.getEmail());
        }

        return ok("发送激活邮件成功",true);
    }

    @RequestMapping(value = RestConstant.REST_ACTIVATE_ACTION,method = RequestMethod.POST)
    public ResponseEntity<RestResult> activate(@RequestBody InputUser user){

        boolean result = activateUser(user.getCode());
        return ok("激活成功");
    }


    @RequestMapping(value = RestConstant.REST_LOGIN_ACTION,method= RequestMethod.POST)
    public ResponseEntity<RestResult> login(@RequestBody InputUser login){

        UserInfo user = authService.login(login.getUsername(),login.getPassword());

        return ok("登录成功",user);
    }


    @RequestMapping(value = "/logout")
    public ResponseEntity<RestResult> logout(){

        boolean result = authService.logout();

        return ok(result);
    }


    @RequestMapping(value = RestConstant.REST_EXISTS_ACTION+"/{prop}/{value:.+}")
    public ResponseEntity<RestResult> exists(@PathVariable String prop, @PathVariable String value){
        boolean result = false;
        if(prop.toLowerCase().equals(USER_NAME)){
            result = userManager.userExists(value);
        }
        else if(prop.toLowerCase().equals(USER_EMAIL)){
            result = userManager.userExistsByEmail(value);
        }
        else if(prop.toLowerCase().equals(USER_PHONE)){
            result = userManager.userExistsByPhone(value);
        }
        else{
            throw new RestException(String.format("无法识别的参数：%s",prop),HttpStatus.BAD_REQUEST);
        }

        return ok(new UserExists(result));
    }


    @RequestMapping(value ="/changepwd",method = RequestMethod.POST)
    public ResponseEntity<RestResult> changePassword(@RequestBody InputUser changePwd){
        try{
            userManager.changePassword(changePwd.getPassword(),changePwd.getNewPwd());
            return ok("密码修改成功",true);
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = RestConstant.REST_RESETPWD_ACTION,method=RequestMethod.POST)
    public ResponseEntity<RestResult> resetPassword(@RequestBody InputUser user){
        if(redisTemplate.hasKey(user.getCode())){
            String username = (String)redisTemplate.opsForValue().get(user.getCode());
            if(username!=null){
                try{
                    userManager.resetPassword(username,user.getNewPwd());
                }
                catch (Exception e){
                    throw new AuthException("重置密码失败");
                }
                return ok("重置密码成功",true);
            }
            else{
                throw new AuthException(String.format("重置码错误"),HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new AuthException(String.format("重置码已过期"),HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/changemail")
    public ResponseEntity<RestResult> changeEmail(InputUser user){
        String email = user.getEmail();
        if(!RegexHelper.isEmail(email)){
            throw new AuthException(String.format("新邮箱地址不合法，修改失败"));
        }

        try{
            userManager.changeEmail(email);
            return ok("邮箱修改成功",true);
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    private boolean registerUser(InputUser user){
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
            throw new RestException(e.getMessage());
        }
    }


    private boolean sendResetPasswordEmail(String url,String email){

        if(!RegexHelper.isEmail(email))
            throw new AuthException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);

        User user=null;
        try{
            user =(User) userManager.loadUserByEmail(email);
            if(user==null)
                throw new AuthException(String.format("邮箱%s未注册",email));
        }catch (Exception e){
            throw new AuthException(e.getMessage());
        }

        String content = generateResetPasswordContent(user.getUsername(),url);
        mailService.sendSimple(email,ACTIVATE_EMAIL_TITLE,content,true);
        return true;



    }

    private boolean sendActivateEmail(String url,String username){

        User user=null;
        try{
            user =(User) userManager.loadUserByUsername(username);
        }catch (Exception e){
            throw new RestException(e.getMessage());
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
        String code = generateCacheCode(username);
        url = generateClickUrl(url,code);
        return String.format("尊敬的国芯开发者%s<br>你好！<br>请<a href='%s'>点此</a>以激活账号，或复制如下链接到浏览器中打开：<br>%s。",username,url,url);

    }

    private String generateCacheCode(String value){
        String code = UUID.randomUUID().toString().replace("-","").toLowerCase();
        redisTemplate.opsForValue().set(code,value,validationExpiration, TimeUnit.SECONDS);
        return code;
    }

    private String generateClickUrl(String url, String code){
        StringBuilder sb = new StringBuilder(url);
        return sb.append(String.format("?code=%s",code))
                .toString();
    }

    private boolean activateUser(String code){

        if(redisTemplate.hasKey(code)){
            String username = (String)redisTemplate.opsForValue().get(code);
            if(username!=null){
                try{
                    userManager.activateUser(username);
                }
                catch (Exception e){
                    throw new RestException("激活失败");
                }
                return true;
            }
            else{
                throw new RestException(String.format("激活码错误"),HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new RestException(String.format("激活码已过期"),HttpStatus.BAD_REQUEST);
        }

    }


    private String generateResetPasswordContent(String username,String url){
        String code = generateCacheCode(username);
        url = generateClickUrl(url,code);

        return String.format("尊敬的国芯开发者%s<br>你好！<br>请<a href='%s'>点此</a>以重置密码，或复制如下链接到浏览器中打开：<br>%s。",username,url,url);

    }


}
