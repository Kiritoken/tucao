package com.eli.emailserver.controller;


import com.eli.emailserver.constant.QiNiuConstant;
import com.eli.emailserver.entity.QiNiuFile;
import com.eli.emailserver.entity.ResultBean;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.Impl.QiniuUploadFileServiceImpl;
import com.eli.emailserver.service.Impl.UserServiceImpl;
import com.qiniu.http.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;


/**
 * 用户修改信息操作
 * @author Eli
 */
@RestController
@CrossOrigin
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private QiniuUploadFileServiceImpl qiniuUploadFileService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "user/update/avatar",method = RequestMethod.POST)
    @RequiresRoles(value = {"normal_user","member_user","admin"},logical = Logical.OR)
    @RequiresPermissions("user:update")
    @ResponseBody
    public ResultBean<Object> updateAvatar(MultipartFile file){
        ResultBean<Object> resultBean = new ResultBean<>();
        if(Objects.isNull(file) || file.isEmpty()){
            logger.error("上传的文件为空");
            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("上传的文件为空!");
            return resultBean;
        }
        try {
            Response response = qiniuUploadFileService.uploadFile(file.getInputStream());
            QiNiuFile qiNiuFile = response.jsonToObject(QiNiuFile.class);
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            user.setAvatarUrl(QiNiuConstant.OUTER_LINK_ADDRESS + qiNiuFile.getKey());
            user.setUpdateTime(new Date());
            userService.updateUser(user);
            logger.info(user.getAvatarUrl());

            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("上传头像成功");
            resultBean.setData(user);
            return resultBean;
        }catch (Exception e){
            logger.info(e.getMessage());
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setMsg("服务器错误!");
            return resultBean;
        }
    }
}
