package com.eli.emailserver;

import com.eli.emailserver.dao.UserMapper;
import com.eli.emailserver.entity.QiNiuFile;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.Impl.QiniuUploadFileServiceImpl;
import com.eli.emailserver.utils.RedisConstants;
import com.eli.emailserver.utils.RedisUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServerApplicationTests {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private QiniuUploadFileServiceImpl qiniuUploadFileService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		 Page page = PageHelper.startPage(2,4);
         List<User> userList = mapper.getUserList();
         userList.forEach(user -> System.out.println(user.toString()));
	}
}
