package com.eli.emailserver.shiro;

import com.eli.emailserver.entity.Permission;
import com.eli.emailserver.entity.Role;
import com.eli.emailserver.entity.User;
import com.eli.emailserver.service.Impl.PermissionServiceImpl;
import com.eli.emailserver.service.Impl.RoleServiceImpl;
import com.eli.emailserver.service.Impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


/**
 * 获取用户权限信息
 * @author Eli
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PermissionServiceImpl permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("正在授权:"+principalCollection.getPrimaryPrincipal());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取用户
        User user = userService.getUserByName((String)principalCollection.getPrimaryPrincipal());
        //获取角色与权限
        Set<Role> roles = roleService.getRoleListByUser(user);
        Set<Permission> permissionSet = new HashSet<>();
        for(Role role:roles){
            authorizationInfo.addRole(role.getRoleName());
            System.out.println(role.getRoleName());
            permissionSet.addAll(permissionService.getPermissionListByRoleId(role.getId()));
        }

        for(Permission permission :permissionSet){
            authorizationInfo.addStringPermission(permission.getPermissionCode());
        }
        return authorizationInfo;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String)authenticationToken.getPrincipal();
        logger.info(userName + "正在授权");
        //  获取数据库中对应用户信息
        User user =  userService.getUserByName(userName);
        if(user != null){
            //TODO redis session 管理
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        }else{
            return null;
        }
    }
}
