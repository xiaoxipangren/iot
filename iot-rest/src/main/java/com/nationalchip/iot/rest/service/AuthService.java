package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.Admin;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.data.model.hub.Developer;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.model.auth.UserInfo;
import com.nationalchip.iot.rest.model.auth.UserRegister;
import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.security.provider.IJwtProvider;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/25/18 4:45 PM
 * @Modified:
 */
@Service
public class AuthService {




    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private IJwtProvider jwtProvider;



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MailService mailService;

    @Autowired
    private ITenantAware tenantAware;




    public UserInfo login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);

        //auth登录后保存登录状态没有任何意义，只需返回token即可
        //SecurityContextHolder.getContext().setAuthentication(authentication);


        User user = (User) authentication.getPrincipal();
        Status status = user.getStatus();
        String jwt="";
        switch (status){
            case REGISTERED:
                jwt="";
                break;
            case ACTIVED:
                jwt = jwtProvider.generateToken(authentication);
                break;
            case PENDING:
                jwt="";
                break;
            default:
                jwt = jwtProvider.generateToken(authentication);
                break;

        }


        return new UserInfo(user.getUsername(),status,jwt);

    }

    public boolean logout(String token){
        String username = tenantAware.getCurrentTenant();

        Date expiration = jwtProvider.getExpiration(token);

        //redis的过期时间与token的过期时间相同
        redisTemplate.opsForValue().set(KeyHelper.tokenExpirationKey(username),true,expiration.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return true;
    }

    public boolean changePassword(String oldPwd,String newPwd){
        try{
            userManager.changePassword(oldPwd,newPwd);
            return true;
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public boolean sendResetPasswordEmail(String url,String email){

        if(!RegexHelper.isEmail(email))
            throw new AuthException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);

        User user=null;
        try{
            user =(User) userManager.loadUserByUsername(email);
            if(user==null)
                throw new AuthException(String.format("邮箱%s未注册",email));
        }catch (Exception e){
            throw new AuthException(e.getMessage());
        }

        String content = generateResetPasswordContent(user.getUsername(),url);
        mailService.sendSimple(email,ACTIVATE_EMAIL_TITLE,content,true);
        return true;



    }

    public boolean resetPassword(String username,String newPwd, String code){
        String key  = KeyHelper.resetPasswordKey(username);
        if(redisTemplate.hasKey(key)){
            String cacheCode = (String)redisTemplate.opsForValue().get(key);
            if(cacheCode!=null && cacheCode.equals(code)){
                try{
                    userManager.resetPassword(username,newPwd);
                }
                catch (Exception e){
                    throw new AuthException("重置密码失败");
                }
                return true;
            }
            else{
                throw new AuthException(String.format("重置码错误"),HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new AuthException(String.format("重置码已过期"),HttpStatus.BAD_REQUEST);
        }


    }

    public boolean changeEmail(String email){

        if(!RegexHelper.isEmail(email)){
            throw new AuthException(String.format("新邮箱地址不合法，修改失败"));
        }

        try{
            userManager.changeEmail(email);
            return true;
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public boolean usernameExists(String username){
        return userManager.userExists(username);
    }

    public boolean phoneExists(String phone){
        return userManager.userExistsByPhone(phone);
    }

    public boolean emailExists(String email){
        return userManager.userExistsByEmail(email);
    }

    public String refresh(String token){
        return null;
    }

    private void exists(User user){
        if(user==null)
            throw new AuthException(String.format("用户%s不存在",user.getUsername()));
    }

    private String generateResetPasswordContent(String username,String url){
        String key = KeyHelper.resetPasswordKey(username);
        String code = generateCacheCode(key);
        url = generateClickUrl(url,username,code);

        return String.format("尊敬的国芯开发者%s<br>你好！<br>请<a href='%s'>点此</a>以重置密码，或复制如下链接到浏览器中打开：<br>%s。",username,url,url);

    }






}
