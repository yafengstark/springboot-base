使用了Swagger2

[文档html](http://localhost:8081/api/v1/swagger-ui.html)



## 数据表设计

### tb_role

- role_code
- role_name:
  - sysadmin\admin\user

### tb_user



- user_no
- mobile
- user_name
- pass_word
- email
- create_time
- avatar: 头像
- status：状态值（1：启用，2：禁用，3：删除）
- job：

### tb_sms_verify 验证码



captcha: 验证码

- sms_verify_id： 主键
- sms_id：短信编号（可以自己生成，也可以第三方复返回）
- mobile：电话号码
- sms_verify：验证码类型（1：登录验证，2：注册验证，3：忘记密码，4：修改账号）
- create_time：发送时间





