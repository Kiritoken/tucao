package com.eli.emailserver.controller;


import com.alibaba.fastjson.JSON;
import com.eli.emailserver.constant.MailConstant;
import com.eli.emailserver.constant.RoleConstant;
import com.eli.emailserver.entity.MailMessage;
import com.eli.emailserver.entity.ResultBean;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.Impl.MailServiceImpl;
import com.eli.emailserver.service.Impl.UserServiceImpl;
import com.eli.emailserver.utils.RedisConstants;
import com.eli.emailserver.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 登陆controller
 * @author Eli
 */
@RestController
@CrossOrigin
public class LoginController  extends BaseController{
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MailServiceImpl mailService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String hashAlgorithmName="MD5";
    private static final int hashIterations = 2;

    @RequestMapping("/login/auth")
    @ResponseBody
    public ResultBean<Object> login(@RequestBody @Valid User user, BindingResult bindingResult){
        ResultBean<Object> resultBean =new ResultBean<>();
        //请求的参数不通过
        if(bindingResult.hasErrors()){
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setMsg("错误的参数");
            return resultBean;
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        currentUser.login(token);
        resultBean.setCode(ResultBean.SUCCESS);
        resultBean.setMsg("登陆成功");
        Map<String,Object> map = new HashMap<>(16);
        map.put("user",userService.getUserByName(user.getUsername()));
        map.put("token",SecurityUtils.getSubject().getSession().getId());
        resultBean.setData(map);
        return resultBean;
    }


    /**
     * 注册
     * @param user 用户
     * @return json
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> register(@RequestBody @Valid User user, BindingResult bindingResult){
        ResultBean<Object> resultBean =new ResultBean<>();
        //请求的参数不通过
        if(bindingResult.hasErrors()){
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setMsg("错误的参数");
            return resultBean;
        }
        // 检查用户是否已经注册
        //已经存在
        if(userService.isExistByUserName(user.getUsername())){
            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("用户已经注册!");
            return resultBean;
        }else{
            //未激活用户信息存入数据库中
            user.setRoleId(RoleConstant.UNACTIVATED_USER);
            user.setCreateTime(new Date());
            user.setUpdateTime(user.getCreateTime());
            user.setDeleteStatus("1");
            user.setPassword(new SimpleHash(hashAlgorithmName, user.getPassword(), null, hashIterations).toString());
            userService.createUser(user);
            // 生成激活验证码，存入redis中
            String codeValue = UUID.randomUUID().toString();
            String key = RedisConstants.REGISTER_KEY_PREFIX + user.getUsername();
            redisUtil.setex(key,codeValue,RedisConstants.REGISTER_EXPIRE_TIME,RedisConstants.datebase1);

            // RabbitMQ解耦  发送验证邮件
            String messageID = System.currentTimeMillis()+"$"+codeValue;
            Map<String,String> map = new HashMap<>();
            map.put("userName",user.getUsername());
            map.put("code",codeValue);
            MailMessage mailMessage = new MailMessage(user.getUsername(), user.geteMail(),
                    MailConstant.REGISTER_SUBJECT,MailConstant.FROM, JSON.toJSONString(map),"regTemplate",
                    messageID, MailConstant.REGISTER_ROUTING_KEY);
            mailService.sendMail2MQ(mailMessage);

            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("注册成功，请点击邮箱验证链接进行验证!");
            resultBean.setData(user);
            return resultBean;
        }
    }



    @RequestMapping("/login/activate")
    public String activateUser(@RequestParam("userName") String userName,@RequestParam("code")String code){
        logger.info(userName+"正在激活账户");
        String confirmCode = redisUtil.get(RedisConstants.REGISTER_KEY_PREFIX+userName,RedisConstants.datebase1);
        if(confirmCode!= null && confirmCode.equals(code)){
            User user = userService.getUserByName(userName);
            //普通用户
            user.setRoleId(RoleConstant.NORMAL_USER);
            userService.updateUser(user);
            logger.info("激活成功!");
            return "redirect: http://localhost:8020/login";
        }else{
            logger.info("激活失败!");
            return "redirect: http://localhost:8020/register";
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    @RequiresRoles("normal_user")
    public void test(@RequestParam("userName") String userName){
        System.out.println(userName);
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println(session.getAttribute("user"));
    }
}
