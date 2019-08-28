

[swagger2 官网](https://swagger.io/)







样例：

```java
@Api("用户信息管理")
@RestController
@RequestMapping("/user/*")
public class UserController {

    private final static List<User> userList = new ArrayList<>();

    {
        userList.add(new User("1", "admin", "123456"));
        userList.add(new User("2", "jacks", "111111"));
    }

    @ApiOperation("获取列表")
    @GetMapping("list")
    public List userList() {
        return userList;
    }

    @ApiOperation("新增用户")
    @PostMapping("save")
    public boolean save(User user) {
        return userList.add(user);
    }

    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "user", value = "单个用户信息", dataType = "User")
    @PutMapping("update")
    public boolean update(User user) {
        return userList.remove(user) && userList.add(user);
    }

    @ApiOperation("批量删除")
    @ApiImplicitParam(name = "users", value = "N个用户信息", dataType = "List<User>")
    @DeleteMapping("delete")
    public boolean delete(@RequestBody List<User> users) {
        return userList.removeAll(users);
    }
}
```

- post 参数为json
```java
// 指定post请求的参数列表，返回格式等
@ApiOperation(value = "手机用户名邮箱密码登录", notes = "body体参数,不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            // 内含参数1
            @ApiImplicitParam(name = "requestJson",
                    value = "{\"identity\":\"13888888888\",\"password\":\"123456\"}", 
                    required = true,
                    dataType = "String",
                    paramType = "body"),
    })
    @PostMapping("/login")
```

```
Implicit： 内含的
```

- get 参数在路径中

```java
 @ApiOperation(value = "获取角色详细信息", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",
                    name = "roleCode",
                    dataType = "String",
                    value = "role-f7943542d87a4f028f446b71d9ede25d",
            required = true )
    })
    @GetMapping(value = "/{roleCode}")
    public ResponseModel getById(@PathVariable("roleCode") String roleCode)throws Exception{
        return ResponseHelper.buildResponseModel(roleService.selectByRoleCode(roleCode));
    }
```



- 完整样例

```java
package com.xxx.firstboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xxx.firstboot.domain.User;
import com.xxx.firstboot.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@Api("userController相关api")
public class UserController {

    @Autowired
    private UserService userService;
    
//    @Autowired
//    private MyRedisTemplate myRedisTemplate;

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
       // 放在header中的参数
        @ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
       // 参数在路径中
        @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="wangna")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/getUser",method=RequestMethod.GET)
    public User getUser(@RequestHeader("username") String username, @RequestParam("password") String password) {
        return userService.getUser(username,password);
    }
    
//    @RequestMapping("/testJedisCluster")
//    public User testJedisCluster(@RequestParam("username") String username){
//        String value =  myRedisTemplate.get(MyConstants.USER_FORWARD_CACHE_PREFIX, username);
//        if(StringUtils.isBlank(value)){
//            myRedisTemplate.set(MyConstants.USER_FORWARD_CACHE_PREFIX, username, JSON.toJSONString(getUser()));
//            return null;
//        }
//        return JSON.parseObject(value, User.class);
//    }
    
}
```



