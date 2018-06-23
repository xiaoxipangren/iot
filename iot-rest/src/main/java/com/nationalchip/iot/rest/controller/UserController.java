//package com.nationalchip.iot.rest.controller;
//
//import com.nationalchip.iot.cache.helper.KeyHelper;
//import com.nationalchip.iot.common.io.IFileRepository;
//import com.nationalchip.iot.data.builder.IUserBuilder;
//import com.nationalchip.iot.data.configuration.DataProperties;
//import com.nationalchip.iot.data.manager.IUserManager;
//import com.nationalchip.iot.data.model.auth.IUser;
//import com.nationalchip.iot.helper.RegexHelper;
//import com.nationalchip.iot.rest.configuration.RestProperty;
//import com.nationalchip.iot.rest.exception.RestException;
//import com.nationalchip.iot.rest.resource.Response;
//import com.nationalchip.iot.rest.resource.auth.*;
//import com.nationalchip.iot.rest.service.MailService;
//import com.nationalchip.iot.security.configuration.RestConstant;
//import com.nationalchip.iot.tenancy.ITenantAware;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @Author: zhenghq
// * @Description:
// * @Date: 5/22/18 8:49 AM
// * @Modified:
// */
//
//@RestController
//@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_USER_MAPPING)
//@Api(tags = "用户API")
//public class UserController extends BaseController{
//
//    private final static String USER_NAME="username";
//    private final static String USER_PHONE="phone";
//    private final static String USER_EMAIL="email";
//    private static final String VALIDATE_EMAIL_TITLE ="国芯开发者账号注册验证码";
//    private static final String RESETPWD_EMAIL_TITLE ="国芯开发者账号重置密码验证码";
//    private static final String ACTION_VALIDATE="validate";
//    private static final String ACTION_RESETPWD="resetpwd";
//    private static final String AVATAR="avatar";
//
//
//    @Autowired
//    private IUserManager userManager;
//
//    @Autowired
//    private MailService mailService;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    private RestProperty restProperty;
//
//    @Autowired
//    private ITenantAware tenantAware;
//
//    @Qualifier("hashFileRepository")
//    @Autowired
//    private IFileRepository fileRepository;
//
//    @Autowired
//    private DataProperties dataProperties;
//
//
//    @ApiOperation(value ="注册新用户",notes = "根据输入的用户名、邮箱和密码注册新用户")
//    @ApiImplicitParam(name = "registerUser",value = "注册用户",paramType = "body",dataType = "RegisterUser",
//            required = true,examples=@Example(value = {
//            @ExampleProperty(mediaType = "application/json",value = "{\"username\":\"username\"}")
//    }))
//    @RequestMapping(value = RestConstant.REST_REGISTER_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
//    public ResponseEntity<Response> register(@RequestBody RegisterUser registerUser){
//        validateCode(registerUser.getEmail(),ACTION_VALIDATE,registerUser.getCode());
//        boolean result = registerUser(registerUser);
//        return success("注册成功",result, HttpStatus.CREATED);
//    }
//
//    @ApiOperation(value = "检测验证码是否正确",notes = "验证验证码是否合法有效")
//    @ApiImplicitParam(name="validateCode",value = "待验证的邮件验证码信息，包括验证码的接收邮箱，验证码类型等",paramType = "body",
//            dataType = "ValidateCode",required = true)
//    @RequestMapping(value = RestConstant.REST_VALIDATE_ACTION,method = RequestMethod.POST)
//    public ResponseEntity<Response> validateCode(@RequestBody ValidateCode validateCode){
//        return ResponseEntity.ok(new Response(validateCode(validateCode.getEmail(),validateCode.getAction(),validateCode.getCode())));
//    }
//
//
//    @ApiOperation(value = "发送验证码邮件",notes = "发送验证码邮件")
//    @ApiImplicitParam(name="sendMail",value = "要发送的邮件信息",paramType = "body",
//            dataType = "SendMail",required = true)
//    @RequestMapping(value = RestConstant.REST_SENDMAIL_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
//    public ResponseEntity<Response> sendMail(@RequestBody SendMail sendMail){
//
//
//        if(sendMail.getAction().toLowerCase().equals(ACTION_VALIDATE)){
//            sendValidateEmail(sendMail.getEmail());
//            return ok("发送验证邮件成功",true);
//        }
//        else if(sendMail.getAction().equals(ACTION_RESETPWD)){
//            sendResetPasswordEmail(sendMail.getEmail());
//            return ok("发送重置密码邮件成功",true);
//        }
//        else{
//            throw new RestException("无法识别的操作",HttpStatus.BAD_REQUEST);
//        }
//
//    }
//
//
//
//    @ApiOperation(value = "根据某一属性验证用户是否存在")
//    @ApiImplicitParams({@ApiImplicitParam(name = "prop",value = "属性名",dataType = "String",paramType = "path",required = true),
//            @ApiImplicitParam(name="value",value = "属性值",dataType = "String",paramType = "path",required = true)})
//    @RequestMapping(value = RestConstant.REST_EXISTS_ACTION+"/{prop}/{value:.+}",method = RequestMethod.GET)
//    public ResponseEntity<Response> exists(@PathVariable String prop, @PathVariable String value){
//        boolean result = false;
//        if(prop.toLowerCase().equals(USER_NAME)){
//            result = userManager.userExists(value);
//        }
//        else if(prop.toLowerCase().equals(USER_EMAIL)){
//            result = userManager.existsByEmail(value);
//        }
//        else if(prop.toLowerCase().equals(USER_PHONE)){
//            result = userManager.existsByPhone(value);
//        }
//        else{
//            throw new RestException(String.format("无法识别的参数：%s",prop),HttpStatus.BAD_REQUEST);
//        }
//
//        return ok(new UserExists(result));
//    }
//
//
//    @ApiOperation(value = "修改密码",notes = "修改用户密码，该API需要token鉴权")
//    @ApiImplicitParams({@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
//            dataType = "String",required = true),@ApiImplicitParam(name="changePwd",value = "修改密码信息，包括旧密码和新密码",paramType = "body",
//            dataType = "ChangepwdUser",required = true)})
//    @RequestMapping(value ="/changepwd",method = RequestMethod.POST)
//    public ResponseEntity<Response> changePassword(@RequestBody ChangepwdUser changePwd){
//        try{
//            userManager.changePassword(changePwd.getOldPwd(),changePwd.getNewPwd());
//            return ok("密码修改成功",true);
//        }
//        catch (Exception e){
//            throw new RestException(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @ApiOperation(value = "重置密码",notes = "通过注册邮箱重置密码")
//    @ApiImplicitParam(name="pwdReset",value = "重置密码信息，包括绑定的email，验证码和新密码",paramType = "body",
//            dataType = "PwdReset",required = true)
//    @RequestMapping(value = RestConstant.REST_RESETPWD_ACTION,method=RequestMethod.POST)
//    public ResponseEntity<Response> resetPassword(@RequestBody PwdReset pwdReset){
//        validateCode(pwdReset.getEmail(),ACTION_RESETPWD,pwdReset.getCode());
//        try{
//            userManager.resetPassword(pwdReset.getEmail(),pwdReset.getPassword());
//        }
//        catch (Exception e){
//            throw new RestException("重置密码失败");
//        }
//        return ok("重置密码成功",true);
//    }
//
//    @ApiOperation(value = "修改邮箱",notes = "重新绑定邮箱，该API需要token鉴权")
//    @ApiImplicitParams({@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
//            dataType = "String",required = true),@ApiImplicitParam(name="changemailUser",value = "新邮箱地址",paramType = "body",
//            dataType = "ChangemailUser",required = true)})
//    @RequestMapping(value = "/changemail",method = RequestMethod.POST)
//    public ResponseEntity<Response> changeEmail(@RequestBody ChangemailUser changemailUser){
//        String email = changemailUser.getEmail();
//        if(!RegexHelper.isEmail(email)){
//            throw new RestException(String.format("新邮箱地址不合法，修改失败",email));
//        }
//
//        try{
//            userManager.changeEmail(email);
//            return ok("邮箱修改成功",email);
//        }
//        catch (Exception e){
//            throw new RestException(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @RequestMapping(value = "/changeavatar",method = RequestMethod.POST)
//    public ResponseEntity<Response> changeAvatar(@RequestParam(value = "file") MultipartFile avatar){
//        String path;
//        try {
//            path = userManager.changeAvatar(avatar.getInputStream());
//        } catch (IOException e) {
//            throw new RestException("上传头像失败");
//        }
//        return ok("修改头像成功",path);
//    }
//
//
//    private boolean registerUser(RegisterUser user){
//
//        try{
//
//            int type = user.getType();
//            if(type==1)//禁止注册管理员
//                user.setType(0);
//
//
//            IUserBuilder builder = getBuilderFactory().user();
//            builder.avatar("avatar.jpg")
//                    .email(user.getEmail())
//                    .password(user.getPassword())
//                    .phone(user.getPhone())
//                    .type(user.getType())
//                    .name(user.getUsername());
//
//            userManager.create(builder);
//
//            return true;
//        }
//        catch (Exception e){
//            throw new RestException(e.getMessage());
//        }
//    }
//
//
//    private boolean sendResetPasswordEmail(String email){
//
//        if(!RegexHelper.isEmail(email))
//            throw new RestException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);
//
//        IUser user=null;
//        try{
//            user = userManager.findByEmail(email);
//            if(user==null)
//                throw new RestException(String.format("邮箱%s未注册",email));
//        }catch (Exception e){
//            throw new RestException(e.getMessage());
//        }
//
//        String content = generateResetPasswordContent(email);
//        mailService.sendSimple(email, RESETPWD_EMAIL_TITLE,content,true);
//        return true;
//
//
//
//    }
//
//    private boolean sendValidateEmail(String email){
//
//        if(!RegexHelper.isEmail(email))
//            throw new RestException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);
//
//        if(userManager.existsByEmail(email)){
//            throw new RestException(String.format("邮箱%s已被绑定",email),HttpStatus.BAD_REQUEST);
//        }
//
//
//
//        String content = generateValidateContent(email);
//
//        mailService.sendSimple(email, VALIDATE_EMAIL_TITLE,content,true);
//
//        return true;
//
//    }
//
//    private String generateValidateContent(String email){
//        String code = generateCacheCode(generateCacheKey(email,ACTION_VALIDATE));
//        return String.format(restProperty.getValidateMail(),code);
//
//    }
//
//    private String generateCacheCode(String key){
//        String code = UUID.randomUUID().toString().replace("-","").toLowerCase().substring(0,4);
//        redisTemplate.opsForValue().set(key.toLowerCase(),code,restProperty.getValidationExpiration(), TimeUnit.SECONDS);
//        return code;
//    }
//
//
//
//    private String generateResetPasswordContent(String email){
//        String code = generateCacheCode(generateCacheKey(email,ACTION_RESETPWD));
//
//        return String.format(restProperty.getResetpwdMail(),code);
//
//    }
//
//
//    private String generateCacheKey(String email,String prefix){
//        if(prefix.equalsIgnoreCase(ACTION_RESETPWD))
//            return KeyHelper.resetPasswordKey(email);
//        else if(prefix.equalsIgnoreCase(ACTION_VALIDATE))
//            return KeyHelper.validateEmailKey(email);
//        return String.format("%s-%s",prefix,email);
//    }
//
//    private boolean validateCode(String email,String prefix,String code){
//        String key = generateCacheKey(email,prefix);
//        if(!redisTemplate.hasKey(key))
//            throw new RestException("验证码已过期",HttpStatus.BAD_REQUEST);
//        String cacheCode = (String)redisTemplate.opsForValue().get(key);
//        if(cacheCode == null)
//            throw new RestException("验证码不存在",HttpStatus.BAD_REQUEST);
//        if(!cacheCode.equalsIgnoreCase(code))
//            throw new RestException("验证码错误",HttpStatus.BAD_REQUEST);
//        return true;
//    }
//
//}
