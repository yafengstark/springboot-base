package com.liugh.controller;

import com.alibaba.fastjson.JSONObject;
import com.liugh.annotation.*;
import com.liugh.base.Constant;
import com.liugh.base.PublicResultConstant;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.User;
import com.liugh.service.IUserService;
import com.liugh.util.ComUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * 登录接口
 *
 * @author liugh
 * @since 2018-05-03
 */
@RestController
@Api(description = "身份认证模块")
public class LoginController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "手机用户名邮箱密码登录", notes = "body体参数,不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            // 内含参数
            @ApiImplicitParam(name = "requestJson",// 绑定的是 下面的参数列表中同名的requestJson
                    value = "{\"identity\":\"1\",\"password\":\"123456\"}",
                    required = true,
                    dataType = "String",
                    paramType = "body")
    })
    @PostMapping("/login")
    @Log(action = "SignIn", modelName = "Login", description = "前台密码登录接口") //自定义@Log注解自动记录日志到数据库
    @Pass //自定义@Pass注解接口不用进行认证身份
    //5秒产生一个令牌,放入容量为0.3的令牌桶
    @AccessLimit(perSecond = 0.3, timeOut = 5000)
    public ResponseModel<Map<String, Object>> login(
            @ValidationParam("identity,password")
            @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.buildResponseModel(userService.checkMobileAndPasswd(requestJson));
    }

    //    @ApiIgnore

    /**
     * 添加用户的按钮权限
     * @param requestJson
     * @param user
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "管理员添加用户", notes = "", produces = "application/json")
    @ApiImplicitParams({
            // 内含参数
            @ApiImplicitParam(name = "requestJson",
                    value = "{\"username\":\"tyf\"," +
                            "\"mobile\":\"13888888888\"," +
                            "\"captcha\":\"5780\"," +
                            "\"password\":\"123456\",\n" +
                            "\"rePassword\":\"123456\"," +
                            "\"job\":\"java开发\"," +
                            "\"unit(可不传)\":\"xxx公司\"," +
                            "\"roleName\":\"user\"}",
                    required = true,
                    dataType = "String",
                    paramType = "body")
    })
    @Log(action = "register", modelName = "User", description = "管理员添加用户")
    @PostMapping("/admin/register")
    @RequiresPermissions("user:add")
    public ResponseModel registerByAdmin(
            @ValidationParam("username, roleName") @RequestBody JSONObject requestJson,
            @ApiIgnore @CurrentUser User user) throws Exception {

        requestJson.put("createUser", user.getUserNo());
        logger.debug("当前用户的number"  +user.getUserNo());
        return ResponseHelper.buildResponseModel(userService.insertUserByAdmin(requestJson));
    }


    @ApiOperation(value = "短信验证码登录", notes = "body体参数,不需要Authorization",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"mobile\":\"13888888888\",\"captcha\":\"5780\"}"
                    , required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/login/captcha")
    @Log(action = "SignInByCaptcha", modelName = "Login", description = "前台短信验证码登录接口")
    @Pass
    public ResponseModel<Map<String, Object>> loginBycaptcha(
            @ValidationParam("mobile,captcha") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.buildResponseModel(userService.checkMobileAndCatcha(requestJson));
    }


    @ApiOperation(value = "手机验证码注册", notes = "body体参数,不需要Authorization",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"username\":\"liugh\",\"mobile\":\"13888888888\",</br>" +
                    "\"captcha\":\"5780\",\"password\":\"123456\",</br>\"rePassword\":\"123456\",\"job\":\"java开发\"," +
                    "</br>\"unit(可不传)\":\"xxx公司\"}"
                    , required = true, dataType = "String",
                    paramType = "body")
    })
    @PostMapping("/register")
    @Log(action = "register", modelName = "Login", description = "注册接口")
    @Pass
    public ResponseModel<User> register(@ValidationParam("username,password,rePassword,mobile,captcha,job")
                                        @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.buildResponseModel(userService.checkAndRegisterUser(requestJson));
    }


    @ApiOperation(value = "忘记密码通过手机号找回", notes = "body体参数,不需要Authorization。captcha：验证码", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"mobile\":\"17765071662\",\"captcha\":\"5780\",</br>" +
                    "\"password\":\"123456\",\"rePassword\":\"123456\"}"
                    , required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/forget/password")
    @Pass // 不需要认证身份
    public ResponseModel<User> resetPassWord(@ValidationParam("mobile,captcha,password,rePassword")
                                             @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.buildResponseModel(userService.updateForgetPasswd(requestJson));
    }

    /**
     * 检查用户是否注册过
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    @GetMapping("/check/mobile")
    @Pass
    @ApiIgnore
    public ResponseModel loginBycaptcha(@RequestParam("mobile") String mobile) throws Exception {
        User user = userService.getUserByMobile(mobile);
        return ResponseHelper.buildResponseModel(!ComUtil.isEmpty(user));
    }
}
