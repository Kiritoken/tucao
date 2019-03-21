package com.eli.emailserver.controller;


import ch.qos.logback.core.util.TimeUtil;
import com.eli.emailserver.constant.QiNiuConstant;
import com.eli.emailserver.entity.QiNiuFile;
import com.eli.emailserver.entity.ResultBean;
import com.eli.emailserver.entity.Tucao;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.Impl.QiniuUploadFileServiceImpl;
import com.eli.emailserver.service.Impl.TucaoServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiniu.http.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;


/**
 * 吐槽 相关api
 * @author Eli
 */
@RestController
@CrossOrigin
public class TucaoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private QiniuUploadFileServiceImpl qiniuUploadFileService;

    @Autowired
    private TucaoServiceImpl tucaoService;

    @RequestMapping(value = "tucao/list",method = RequestMethod.GET)
/*    @RequiresRoles(value = {"normal_user","member_user","admin"},logical = Logical.OR)
    @RequiresPermissions("tucao:list")*/
    @ResponseBody
    public ResultBean<Object> getTucaoLIst(@RequestParam("pageNum")int pageNum,@RequestParam("pageSize")int pageSize){
           ResultBean<Object> resultBean =new ResultBean<>();
           Page page = PageHelper.startPage(pageNum,pageSize);
           List<Tucao> tucaoList = tucaoService.getTucaoListDescByTime();
           resultBean.setCode(ResultBean.SUCCESS);
           resultBean.setMsg("查询成功");
           resultBean.setData(tucaoList);
           return resultBean;
    }



    @RequestMapping(value = "image/tucao",method = RequestMethod.POST)
    @RequiresRoles(value = {"normal_user","member_user","admin"},logical = Logical.OR)
    @RequiresPermissions("tucao:add")
    @ResponseBody
    public ResultBean<Object> uploadTucaoImage(MultipartFile file)
    {
        ResultBean<Object> resultBean =new ResultBean<>();
        if(Objects.isNull(file) || file.isEmpty()){
            logger.error("上传的文件为空");
            resultBean.setCode(ResultBean.ERROR);
            resultBean.setMsg("上传的文件为空!");
            return resultBean;
        }
        try {
            Response response = qiniuUploadFileService.uploadFile(file.getInputStream());
            QiNiuFile qiNiuFile = response.jsonToObject(QiNiuFile.class);
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            Map<String,String> map = new HashMap<>(16);
            map.put("name",user.getId().toString());
            map.put("url",QiNiuConstant.OUTER_LINK_ADDRESS + qiNiuFile.getKey());

            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("上传图片成功!");
            resultBean.setData(map);
            return resultBean;
        }catch (Exception e){
            logger.info(e.getMessage());
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setMsg("服务器错误,请重新吐槽!");
            return resultBean;
        }
    }


    @RequestMapping(value = "tucao",method = RequestMethod.POST)
    @RequiresRoles(value = {"normal_user","member_user","admin"},logical = Logical.OR)
    @RequiresPermissions("tucao:add")
    @ResponseBody
    public ResultBean<Object> createTucao(@RequestBody @Valid Tucao tucao , BindingResult bindingResult)
    {
        logger.info(tucao.toString());
        ResultBean<Object> resultBean =new ResultBean<>();
        //请求的参数不通过
        if(bindingResult.hasErrors()){
            resultBean.setCode(ResultBean.ERROR);
            resultBean.setMsg("错误的参数");
            return resultBean;
        }
        try {
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
            tucao.setAuthorId(user.getId());
            tucao.setAuthorName(user.getUsername());
            tucao.setAuthorAvatarUrl(user.getAvatarUrl());
            tucao.setCreateTime(new Date());
            tucao.setLikes(0);
            tucao.setDeleteStatus("1");
            tucaoService.createTucao(tucao);

            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setMsg("吐槽成功!");
            return resultBean;
        }catch (Exception e){
            logger.info(e.getMessage());
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setMsg("服务器错误,请重新吐槽!");
            return resultBean;
        }
    }
}
