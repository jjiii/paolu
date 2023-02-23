package com.fc.pay.common.realm;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.StatusEnum;
import com.fc.pay.boss.service.IBossOperatorService;
import com.fc.pay.boss.service.IBossPermissionService;
import com.fc.pay.boss.service.IBossRoleService;

public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private IBossOperatorService operatorService;
	
	@Autowired
	private IBossRoleService roleService;
	
	@Autowired
	private IBossPermissionService permissionService;

	/**
	 * 授权
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BossOperator operator = (BossOperator) session.getAttribute("BossOperator");
		if (operator == null) {
			operator = new BossOperator();
			//operator.setLoginName(loginName);
			operator = operatorService.selectByName(loginName);
			session.setAttribute("BossOperator", operator);
		}
		// 根据登录名查询操作员
		Long operatorId = operator.getId();

		Set<String> roles = (Set<String>) session.getAttribute("ROLES");
		if (roles == null || roles.size()<=0) {
			// 获取用户所有的角色
			roles = roleService.selectRoleOperatorByOperatorId(operatorId);
			session.setAttribute("ROLES", roles);
		}
		// 查询角色信息
		authorizationInfo.setRoles(roles);

		Set<String> permisstions = (Set<String>) session.getAttribute("PERMISSIONS");
		if (permisstions == null || permisstions.isEmpty()) {
			// 获取用户所有的权限
			permisstions = permissionService.selectRolePermissionByOperatorId(operatorId);
			session.setAttribute("PERMISSIONS", permisstions);
		}
		// 根据用户名查询权限
		authorizationInfo.setStringPermissions(permisstions);
		return authorizationInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();
		if (StringUtils.isEmpty(loginName.trim())) {
			throw new UnknownAccountException();// 没找到帐号
		}
		// 根据用户名查找用户
		BossOperator operator = new BossOperator();
		//operator.setLoginName(loginName);
		operator = operatorService.selectByName(loginName);
		
		if (operator == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (StatusEnum.UNACTIVE.name().equals(operator.getStatus())) {
			throw new LockedAccountException(); // 帐号锁定
		}
		
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(operator.getLoginName(), operator.getLoginPwd(),
				ByteSource.Util.bytes(operator.getCredentialsSalt()),getName()); 
		return authenticationInfo;
	}

}
