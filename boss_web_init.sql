-- ----------------------------
-- Table structure for boss_operator
-- ----------------------------
DROP TABLE IF EXISTS `boss_operator`;
CREATE TABLE `boss_operator` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) NOT NULL COMMENT '账号状态，ACTIVE:启用 UNACTIVE:禁用',
  `remark` varchar(300) default NULL COMMENT '备注',
  `real_name` varchar(50) NOT NULL COMMENT '昵称',
  `mobile_no` varchar(15) NOT NULL COMMENT '手机号',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `login_pwd` varchar(256) NOT NULL COMMENT '登录密码',
  `type` int(11) default NULL COMMENT '账号类型 0:超级管理员（唯一，不可删除） 1:普通用户',
  `salt` varchar(50) NOT NULL COMMENT '加密密匙',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员表';

-- ----------------------------
-- Records of boss_operator
-- ----------------------------
INSERT INTO `boss_operator` VALUES ('1', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-12-09 10:30:26', 'ACTIVE', '超级管理员', '超级管理员', '18620936193', 'admin', 'd3c59d25033dbf980d29554025c23a75', '0', '8d78869f470951332959580424d4bf4f');

-- ----------------------------
-- Table structure for boss_menu
-- ----------------------------
DROP TABLE IF EXISTS `boss_menu`;
CREATE TABLE `boss_menu` (
  `id` bigint(20) NOT NULL auto_increment,
  `version` bigint(20) NULL,
  `creater` varchar(50) NULL COMMENT '创建人',
  `create_time` datetime NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) NULL COMMENT '状态 ACTIVE:启用 UNACTIVE:禁用',
  `remark` varchar(300) default NULL COMMENT '备注',
  `is_leaf` varchar(20) default NULL COMMENT '是否是子节点(暂无用)',
  `level` smallint(6) default NULL COMMENT '级别(暂无用)',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单',
  `icon` varchar(100) default NULL COMMENT '图标',
  `number` varchar(20) default NULL COMMENT '顺序编号',
  `name` varchar(100) default NULL COMMENT '菜单名称',
  `url` varchar(100) default NULL COMMENT '菜单地址',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of boss_menu
-- ----------------------------
INSERT INTO `boss_menu` VALUES ('1', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', 'icon-lock2', '001', '权限管理', '##');
INSERT INTO `boss_menu` VALUES ('2', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '1', '', '00101', '菜单管理', 'menu/list');
INSERT INTO `boss_menu` VALUES ('3', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-12-08 11:52:43', 'ACTIVE', '', 'YES', '2', '1', '', '00102', '权限管理', 'permission/listUI');
INSERT INTO `boss_menu` VALUES ('4', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-12-09 13:22:08', 'ACTIVE', '', 'YES', '2', '1', '', '00103', '角色管理', 'role/listUI');
INSERT INTO `boss_menu` VALUES ('5', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-12-09 12:02:44', 'ACTIVE', '', 'YES', '2', '1', '', '00104', '操作员管理', 'operator/listUI');

INSERT INTO `boss_menu` VALUES ('6', '0', '超级管理员', '2016-12-26 11:57:53', '超级管理员', '2016-12-27 10:06:47', 'ACTIVE', NULL, 'NO', '1', '0', ' icon-store2', '002', '商户管理', '');
INSERT INTO `boss_menu` VALUES ('7', '0', '超级管理员', '2016-12-26 11:59:07', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '6', '', '02001', '商户应用管理', 'merchant/list');
INSERT INTO `boss_menu` VALUES ('8', '0', '超级管理员', '2016-12-26 17:07:09', NULL, NULL, 'ACTIVE', NULL, 'NO', '1', '0', 'icon-notebook', '003', '对账管理', '');
INSERT INTO `boss_menu` VALUES ('9', '0', '超级管理员', '2016-12-26 17:10:53', '超级管理员', '2016-12-26 17:24:24', 'ACTIVE', NULL, 'YES', '2', '8', '', '03001', '对账批次管理', 'billbatch/list');
INSERT INTO `boss_menu` VALUES ('10', '0', '超级管理员', '2016-12-27 09:29:42', '超级管理员', '2016-12-27 10:06:16', 'ACTIVE', NULL, 'NO', '1', '0', 'icon-cart-add2', '004', '订单管理', '#');
INSERT INTO `boss_menu` VALUES ('11', '0', '超级管理员', '2016-12-27 09:31:47', '超级管理员', '2016-12-27 11:52:05', 'UNACTIVE', NULL, 'YES', '2', '10', '', '04001', '(旧)支付订单管理', 'order/pay/list');
INSERT INTO `boss_menu` VALUES ('12', '0', '超级管理员', '2016-12-27 09:32:09', '超级管理员', '2016-12-30 11:32:15', 'UNACTIVE', NULL, 'YES', '2', '10', '', '04002', '(旧)退款订单管理', 'order/refund/list');
INSERT INTO `boss_menu` VALUES ('13', '0', '超级管理员', '2016-12-27 11:02:24', '超级管理员', '2016-12-27 11:52:45', 'ACTIVE', NULL, 'YES', '2', '8', '', '03002', '对账汇总管理', 'billsummary/list');
INSERT INTO `boss_menu` VALUES ('14', '0', '超级管理员', '2016-12-27 11:07:01', '超级管理员', '2016-12-27 14:56:57', 'ACTIVE', NULL, 'YES', '2', '8', '', '03003', '对账存疑管理', 'billdoubt/list');
INSERT INTO `boss_menu` VALUES ('15', '0', '超级管理员', '2016-12-27 11:07:24', '超级管理员', '2016-12-27 17:31:29', 'ACTIVE', NULL, 'YES', '2', '8', '', '03004', '对账差错管理', 'billmistake/list');
INSERT INTO `boss_menu` VALUES ('16', '0', '超级管理员', '2017-01-06 10:54:03', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '10', '', '04003', '订单支付管理', 'order/pay/reconcilelist');
INSERT INTO `boss_menu` VALUES ('17', '0', '超级管理员', '2017-01-06 10:55:46', '超级管理员', '2017-01-06 10:59:55', 'ACTIVE', NULL, 'YES', '2', '10', '', '04004', '退款订单管理', 'order/refund/reconcilelist');
INSERT INTO `boss_menu` VALUES ('18', '0', '超级管理员', '2017-01-14 17:15:00', '超级管理员', '2017-01-14 17:15:24', 'UNACTIVE', NULL, 'YES', '2', '8', '', '03005', '对账明细管理', 'billitem/list');
INSERT INTO `boss_menu` VALUES ('19', '0', '超级管理员', '2017-01-16 18:13:24', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '1', '', '00105', '数据字典管理', 'dictionary/list');



-- ----------------------------
-- Table structure for boss_role
-- ----------------------------
DROP TABLE IF EXISTS `boss_role`;
CREATE TABLE `boss_role` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `version` bigint(20) default NULL,
  `creater` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) default NULL COMMENT '状态 ACTIVE:启用 UNACTIVE:禁用',
  `remark` varchar(300) default NULL COMMENT '备注',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of boss_role
-- ----------------------------
INSERT INTO `boss_role` VALUES ('1', '0', 'admin', '2016-06-03 11:07:43', '超级管理员', '2016-12-14 16:02:38', 'ACTIVE', '超级管理员', 'admin', '超级管理员');

-- ----------------------------
-- Table structure for boss_permission
-- ----------------------------
DROP TABLE IF EXISTS `boss_permission`;
CREATE TABLE `boss_permission` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `version` bigint(20) default NULL,
  `creater` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) default NULL COMMENT '状态 ACTIVE:启用 UNACTIVE:禁用',
  `remark` varchar(300) default NULL COMMENT '备注',
  `permission_name` varchar(100) NOT NULL COMMENT '权限名称',
  `permission` varchar(100) NOT NULL COMMENT '权限编码',
  `menu_id` bigint(20) default NULL COMMENT '菜单ID',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`permission`),
  KEY `ak_key_3` (`permission_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of boss_permission
-- ----------------------------
INSERT INTO `boss_permission` VALUES ('1', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-14 16:08:58', 'ACTIVE', '权限管理-菜单-查看', '权限管理-菜单-查看', 'pms:menu:view', '2');
INSERT INTO `boss_permission` VALUES ('2', '2', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '权限管理-菜单-添加', '权限管理-菜单-添加', 'pms:menu:add', '2');
INSERT INTO `boss_permission` VALUES ('3', '2', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '权限管理-菜单-查看', '权限管理-菜单-修改', 'pms:menu:edit', '2');
INSERT INTO `boss_permission` VALUES ('4', '2', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '权限管理-菜单-删除', '权限管理-菜单-删除', 'pms:menu:delete', '2');

INSERT INTO `boss_permission` VALUES ('5', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:27:50', 'ACTIVE', '权限管理-权限-查看', '权限管理-权限-查看', 'pms:permission:view', '3');
INSERT INTO `boss_permission` VALUES ('6', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:28:12', 'ACTIVE', '权限管理-权限-添加', '权限管理-权限-添加', 'pms:permission:add', '3');
INSERT INTO `boss_permission` VALUES ('7', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:29:00', 'ACTIVE', '权限管理-权限-修改', '权限管理-权限-修改', 'pms:permission:edit', '3');
INSERT INTO `boss_permission` VALUES ('8', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:28:40', 'ACTIVE', '权限管理-权限-删除', '权限管理-权限-删除', 'pms:permission:delete', '3');

INSERT INTO `boss_permission` VALUES ('9', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:29:24', 'ACTIVE', '权限管理-角色-查看', '权限管理-角色-查看', 'pms:role:view', '4');
INSERT INTO `boss_permission` VALUES ('10', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:29:37', 'ACTIVE', '权限管理-角色-添加', '权限管理-角色-添加', 'pms:role:add', '4');
INSERT INTO `boss_permission` VALUES ('11', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:29:51', 'ACTIVE', '权限管理-角色-修改', '权限管理-角色-修改', 'pms:role:edit', '4');
INSERT INTO `boss_permission` VALUES ('12', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:30:00', 'ACTIVE', '权限管理-角色-删除', '权限管理-角色-删除', 'pms:role:delete', '4');
INSERT INTO `boss_permission` VALUES ('13', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 16:47:18', 'ACTIVE', '权限管理-角色-分配权限', '权限管理-角色-分配权限', 'pms:role:assignpermission', '4');
INSERT INTO `boss_permission` VALUES ('14', '0', '超级管理员', '2016-12-13 16:46:06', '超级管理员', '2016-12-14 17:25:56', 'ACTIVE', '权限管理-角色-分配菜单', '权限管理-角色-分配菜单', 'pms:role:assignmenu', '4');

INSERT INTO `boss_permission` VALUES ('15', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-06-03 11:07:43', 'ACTIVE', '权限管理-操作员-查看', '权限管理-操作员-查看', 'pms:operator:view', '5');
INSERT INTO `boss_permission` VALUES ('16', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:30:23', 'ACTIVE', '权限管理-操作员-添加', '权限管理-操作员-添加', 'pms:operator:add', '5');
INSERT INTO `boss_permission` VALUES ('17', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:30:33', 'ACTIVE', '权限管理-操作员-修改', '权限管理-操作员-修改', 'pms:operator:edit', '5');
INSERT INTO `boss_permission` VALUES ('18', '0', '超级管理员', '2016-12-16 10:19:51', '超级管理员', '2016-12-13 15:30:33', 'ACTIVE', '权限管理-操作员-删除', '权限管理-操作员-删除', 'pms:operator:delete', '5');
INSERT INTO `boss_permission` VALUES ('19', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:30:47', 'ACTIVE', '权限管理-操作员-冻结与解冻', '权限管理-操作员-冻结与解冻', 'pms:operator:changestatus', '5');
INSERT INTO `boss_permission` VALUES ('20', '0', '超级管理员', '2016-06-03 11:07:43', '超级管理员', '2016-12-13 15:30:57', 'ACTIVE', '权限管理-操作员-重置密码', '权限管理-操作员-重置密码', 'pms:operator:resetpwd', '5');

INSERT INTO `boss_permission` VALUES ('21', '0', '超级管理员', '2016-12-29 10:04:10', NULL, NULL, 'ACTIVE', '商户管理-商户-列表', '商户管理-商户-列表', 'mct:mrechant:list', '7');
INSERT INTO `boss_permission` VALUES ('22', '0', '超级管理员', '2016-12-29 10:04:53', NULL, NULL, 'ACTIVE', '商户管理-商户-查看', '商户管理-商户-查看', 'mct:mrechant:view', '7');
INSERT INTO `boss_permission` VALUES ('23', '0', '超级管理员', '2016-12-29 10:06:24', NULL, NULL, 'ACTIVE', '商户管理-商户-关闭', '商户管理-商户-关闭', 'mct:mrechant:close', '7');
INSERT INTO `boss_permission` VALUES ('24', '0', '超级管理员', '2016-12-29 10:06:43', NULL, NULL, 'ACTIVE', '商户管理-商户-新增', '商户管理-商户-新增', 'mct:mrechant:add', '7');
INSERT INTO `boss_permission` VALUES ('25', '0', '超级管理员', '2016-12-29 10:11:38', NULL, NULL, 'ACTIVE', '商户管理-应用-查看', '商户管理-应用-查看', 'mct:app:view', '7');
INSERT INTO `boss_permission` VALUES ('26', '0', '超级管理员', '2016-12-29 10:12:18', NULL, NULL, 'ACTIVE', '商户管理-应用-关闭', '商户管理-应用-关闭', 'mct:app:close', '7');
INSERT INTO `boss_permission` VALUES ('27', '0', '超级管理员', '2016-12-29 10:28:41', NULL, NULL, 'ACTIVE', '商户管理-应用-新增', '商户管理-应用-新增', 'mct:app:add', '7');
INSERT INTO `boss_permission` VALUES ('28', '0', '超级管理员', '2016-12-29 10:29:18', NULL, NULL, 'ACTIVE', '商户管理-配置-新增', '商户管理-配置-新增', 'mct:config:add', '7');
INSERT INTO `boss_permission` VALUES ('29', '0', '超级管理员', '2016-12-29 10:30:13', NULL, NULL, 'ACTIVE', '商户管理-配置-查看', '商户管理-配置-查看', 'mct:config:view', '7');
INSERT INTO `boss_permission` VALUES ('30', '0', '超级管理员', '2016-12-29 10:30:29', NULL, NULL, 'ACTIVE', '商户管理-配置-关闭', '商户管理-配置-关闭', 'mct:config:close', '7');

INSERT INTO `boss_permission` VALUES ('31', '0', '超级管理员', '2016-12-29 10:53:11', NULL, NULL, 'ACTIVE', '对账管理-批次-列表', '对账管理-批次-列表', 'bill:batch:list', '9');
INSERT INTO `boss_permission` VALUES ('32', '0', '超级管理员', '2016-12-29 10:56:22', NULL, NULL, 'ACTIVE', '对账管理-汇总-列表', '对账管理-汇总-列表', 'bill:summary:list', '13');
INSERT INTO `boss_permission` VALUES ('33', '0', '超级管理员', '2016-12-29 10:57:01', NULL, NULL, 'ACTIVE', '对账管理-存疑-列表', '对账管理-存疑-列表', 'bill:doubt:list', '14');
INSERT INTO `boss_permission` VALUES ('34', '0', '超级管理员', '2016-12-29 10:57:42', NULL, NULL, 'ACTIVE', '对账管理-差错-列表', '对账管理-差错-列表', 'bill:mistake:list', '15');

INSERT INTO `boss_permission` VALUES ('35', '0', '超级管理员', '2016-12-29 11:00:41', NULL, NULL, 'ACTIVE', '订单管理-支付-列表', '订单管理-支付-列表', 'order:pay:list', '11');
INSERT INTO `boss_permission` VALUES ('36', '0', '超级管理员', '2016-12-29 11:01:41', NULL, NULL, 'ACTIVE', '订单管理-支付-查看', '订单管理-支付-查看', 'order:pay:view', '11');
INSERT INTO `boss_permission` VALUES ('37', '0', '超级管理员', '2016-12-29 11:03:20', NULL, NULL, 'ACTIVE', '订单管理-支付-确认平账', '订单管理-支付-确认平账', 'order:pay:settle', '11');
INSERT INTO `boss_permission` VALUES ('38', '0', '超级管理员', '2016-12-29 11:04:11', NULL, NULL, 'ACTIVE', '订单管理-退款-确认平账', '订单管理-退款-确认平账', 'order:refund:settle', '12');
INSERT INTO `boss_permission` VALUES ('39', '0', '超级管理员', '2016-12-29 11:04:30', NULL, NULL, 'ACTIVE', '订单管理-退款-列表', '订单管理-退款-列表', 'order:refund:list', '12');
INSERT INTO `boss_permission` VALUES ('40', '0', '超级管理员', '2016-12-29 11:04:49', NULL, NULL, 'ACTIVE', '订单管理-退款-查看', '订单管理-退款-查看', 'order:refund:view', '12');

INSERT INTO `boss_permission` VALUES ('41', '0', '超级管理员', '2016-12-30 10:23:14', NULL, NULL, 'ACTIVE', '商户管理-应用-列表', '商户管理-应用-列表', 'mct:app:list', '7');
INSERT INTO `boss_permission` VALUES ('42', '0', '超级管理员', '2016-12-30 10:23:33', NULL, NULL, 'ACTIVE', '商户管理-配置-列表', '商户管理-配置-列表', 'mct:config:list', '7');
INSERT INTO `boss_permission` VALUES ('43', '0', '超级管理员', '2016-12-30 10:36:10', NULL, NULL, 'ACTIVE', '对账管理-批次-重启', '对账管理-批次-重启', 'bill:batch:recheck', '9');
INSERT INTO `boss_permission` VALUES ('44', '0', '超级管理员', '2017-01-14 17:16:37', NULL, NULL, 'ACTIVE', '对账管理-明细-列表', '对账管理-明细-列表', 'bill:item:list', '18');

INSERT INTO `boss_permission` VALUES ('45', '0', '超级管理员', '2017-01-06 11:02:45', NULL, NULL, 'ACTIVE', '订单对账确认平帐', '订单对账确认平帐', 'order:pay:reconcilesettle', '16');
INSERT INTO `boss_permission` VALUES ('46', '0', '超级管理员', '2017-01-06 11:03:10', NULL, NULL, 'ACTIVE', '订单支付对账查看帐户', '订单支付对账查看帐户', 'order:pay:reconcileview', '16');
INSERT INTO `boss_permission` VALUES ('47', '0', '超级管理员', '2017-01-06 11:04:13', NULL, NULL, 'ACTIVE', '支付对账获取列表数据', '支付对账列表', 'order:pay:reconcilelist', '16');
INSERT INTO `boss_permission` VALUES ('48', '0', '超级管理员', '2017-01-06 11:04:41', NULL, NULL, 'ACTIVE', '退款订单对账列表数据', '退款订单对账列表', 'order:refund:reconcilelist', '17');
INSERT INTO `boss_permission` VALUES ('49', '0', '超级管理员', '2017-01-06 11:05:01', NULL, NULL, 'ACTIVE', '退款对账确认平帐', '退款对账确认平帐', 'order:refund:reconcilesettle', '17');
INSERT INTO `boss_permission` VALUES ('50', '0', '超级管理员', '2017-01-06 11:05:15', NULL, NULL, 'ACTIVE', '退款对账查看帐户', '退款对账查看帐户', 'order:refund:reconcileview', '17');

INSERT INTO `boss_permission` VALUES ('51', '0', '超级管理员', '2017-01-16 18:14:09', NULL, NULL, 'ACTIVE', '权限管理-数据字典-列表', '权限管理-数据字典-列表', 'pms:dictionary:list', '19');
INSERT INTO `boss_permission` VALUES ('52', '0', '超级管理员', '2017-01-16 18:14:26', NULL, NULL, 'ACTIVE', '权限管理-数据字典-新增', '权限管理-数据字典-新增', 'pms:dictionary:add', '19');
INSERT INTO `boss_permission` VALUES ('53', '0', '超级管理员', '2017-01-16 18:14:42', NULL, NULL, 'ACTIVE', '权限管理-数据字典-修改', '权限管理-数据字典-修改', 'pms:dictionary:edit', '19');
INSERT INTO `boss_permission` VALUES ('54', '0', '超级管理员', '2017-01-16 18:14:55', NULL, NULL, 'ACTIVE', '权限管理-数据字典-删除', '权限管理-数据字典-删除', 'pms:dictionary:delete', '19');



-- ----------------------------
-- Table structure for boss_role_operator
-- ----------------------------
DROP TABLE IF EXISTS `boss_role_operator`;
CREATE TABLE `boss_role_operator` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `creater` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) default NULL COMMENT '状态',
  `remark` varchar(300) default NULL COMMENT '备注',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `operator_id` bigint(20) NOT NULL COMMENT '操作员ID',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`role_id`,`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员与角色关联表';

-- ----------------------------
-- Records of boss_role_operator
-- ----------------------------
INSERT INTO `boss_role_operator` VALUES ('1', '超级管理员', '2016-12-15 20:21:47', null, null, null, null, '1', '1');


-- ----------------------------
-- Table structure for boss_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `boss_menu_role`;
CREATE TABLE `boss_menu_role` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `version` bigint(20) default NULL,
  `creater` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) default NULL COMMENT '状态',
  `remark` varchar(300) default NULL COMMENT '备注',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限与角色关联表';

-- ----------------------------
-- Records of boss_menu_role
-- ----------------------------
INSERT INTO `boss_menu_role` VALUES ('1', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '1');
INSERT INTO `boss_menu_role` VALUES ('2', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '2');
INSERT INTO `boss_menu_role` VALUES ('3', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '3');
INSERT INTO `boss_menu_role` VALUES ('4', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '4');
INSERT INTO `boss_menu_role` VALUES ('5', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '5');
INSERT INTO `boss_menu_role` VALUES ('6', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '6');
INSERT INTO `boss_menu_role` VALUES ('7', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '7');
INSERT INTO `boss_menu_role` VALUES ('8', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '8');
INSERT INTO `boss_menu_role` VALUES ('9', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '9');
INSERT INTO `boss_menu_role` VALUES ('10', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '10');
INSERT INTO `boss_menu_role` VALUES ('11', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '11');
INSERT INTO `boss_menu_role` VALUES ('12', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '12');
INSERT INTO `boss_menu_role` VALUES ('13', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '13');
INSERT INTO `boss_menu_role` VALUES ('14', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '14');
INSERT INTO `boss_menu_role` VALUES ('15', null, '超级管理员', '2016-12-16 15:36:13', null, null, null, null, '1', '15');
INSERT INTO `boss_menu_role` VALUES ('16', NULL, 'admin', '2017-01-06 11:05:30', NULL, NULL, NULL, NULL, '1', '16');
INSERT INTO `boss_menu_role` VALUES ('17', NULL, 'admin', '2017-01-06 11:05:30', NULL, NULL, NULL, NULL, '1', '17');
INSERT INTO `boss_menu_role` VALUES ('18', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '18');
INSERT INTO `boss_menu_role` VALUES ('19', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '19');



-- ----------------------------
-- Table structure for boss_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `boss_role_permission`;
CREATE TABLE `boss_role_permission` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `version` bigint(20) default NULL,
  `creater` varchar(50) default NULL COMMENT '创建人',
  `create_time` datetime default NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `status` varchar(20) default NULL COMMENT '状态',
  `remark` varchar(300) default NULL COMMENT '备注',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY  (`id`),
  KEY `ak_key_2` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限与角色关联表';

-- ----------------------------
-- Records of boss_role_permission
-- ----------------------------

INSERT INTO `boss_role_permission` VALUES ('1', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '1');
INSERT INTO `boss_role_permission` VALUES ('2', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '2');
INSERT INTO `boss_role_permission` VALUES ('3', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '3');
INSERT INTO `boss_role_permission` VALUES ('4', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '4');
INSERT INTO `boss_role_permission` VALUES ('5', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '5');
INSERT INTO `boss_role_permission` VALUES ('6', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '6');
INSERT INTO `boss_role_permission` VALUES ('7', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '7');
INSERT INTO `boss_role_permission` VALUES ('8', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '8');
INSERT INTO `boss_role_permission` VALUES ('9', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '9');
INSERT INTO `boss_role_permission` VALUES ('10', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '10');
INSERT INTO `boss_role_permission` VALUES ('11', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '11');
INSERT INTO `boss_role_permission` VALUES ('12', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '12');
INSERT INTO `boss_role_permission` VALUES ('13', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '13');
INSERT INTO `boss_role_permission` VALUES ('14', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '14');
INSERT INTO `boss_role_permission` VALUES ('15', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '15');
INSERT INTO `boss_role_permission` VALUES ('16', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '16');
INSERT INTO `boss_role_permission` VALUES ('17', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '17');
INSERT INTO `boss_role_permission` VALUES ('18', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '18');
INSERT INTO `boss_role_permission` VALUES ('19', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '19');
INSERT INTO `boss_role_permission` VALUES ('20', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '20');
INSERT INTO `boss_role_permission` VALUES ('21', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '21');
INSERT INTO `boss_role_permission` VALUES ('22', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '22');
INSERT INTO `boss_role_permission` VALUES ('23', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '23');
INSERT INTO `boss_role_permission` VALUES ('24', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '24');
INSERT INTO `boss_role_permission` VALUES ('25', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '25');
INSERT INTO `boss_role_permission` VALUES ('26', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '26');
INSERT INTO `boss_role_permission` VALUES ('27', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '27');
INSERT INTO `boss_role_permission` VALUES ('28', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '28');
INSERT INTO `boss_role_permission` VALUES ('29', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '29');
INSERT INTO `boss_role_permission` VALUES ('30', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '30');
INSERT INTO `boss_role_permission` VALUES ('31', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '31');
INSERT INTO `boss_role_permission` VALUES ('32', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '32');
INSERT INTO `boss_role_permission` VALUES ('33', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '33');
INSERT INTO `boss_role_permission` VALUES ('34', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '34');
INSERT INTO `boss_role_permission` VALUES ('35', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '35');
INSERT INTO `boss_role_permission` VALUES ('36', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '36');
INSERT INTO `boss_role_permission` VALUES ('37', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '37');
INSERT INTO `boss_role_permission` VALUES ('38', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '38');
INSERT INTO `boss_role_permission` VALUES ('39', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '39');
INSERT INTO `boss_role_permission` VALUES ('40', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '40');
INSERT INTO `boss_role_permission` VALUES ('41', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '41');
INSERT INTO `boss_role_permission` VALUES ('42', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '42');
INSERT INTO `boss_role_permission` VALUES ('43', null, '超级管理员', '2016-12-12 16:21:40', null, null, null, null, '1', '43');
INSERT INTO `boss_role_permission` VALUES ('44', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '44');

INSERT INTO `boss_role_permission` VALUES ('45', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '45');
INSERT INTO `boss_role_permission` VALUES ('46', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '46');
INSERT INTO `boss_role_permission` VALUES ('47', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '47');
INSERT INTO `boss_role_permission` VALUES ('48', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '48');
INSERT INTO `boss_role_permission` VALUES ('49', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '49');
INSERT INTO `boss_role_permission` VALUES ('50', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '50');

INSERT INTO `boss_role_permission` VALUES ('51', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '51');
INSERT INTO `boss_role_permission` VALUES ('52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '52');
INSERT INTO `boss_role_permission` VALUES ('53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '53');
INSERT INTO `boss_role_permission` VALUES ('54', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '54');

-- ----------------------------
-- Table structure for boss_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `boss_operator_log`;
CREATE TABLE `boss_operator_log` (
  `id` bigint(20) NOT NULL auto_increment,
  `creater` varchar(50) default NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(50) default NULL COMMENT '修改人',
  `edit_time` datetime default NULL COMMENT '修改时间',
  `version` int(11) NOT NULL default '0' COMMENT '版本号',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人id',
  `operator_name` varchar(50) NOT NULL COMMENT '操作人姓名',
  `operate_type` varchar(50) NOT NULL COMMENT '操作类型( 1:增加，2:修改，3:删除)',
  `ip` varchar(100) NOT NULL COMMENT '地址ip',
  `content` varchar(3000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for boss_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `boss_dictionary`;
CREATE TABLE `boss_dictionary` (
  `id` bigint(11) NOT NULL auto_increment,
  `code` varchar(50) default NULL,
  `name` varchar(200) NOT NULL,
  `parent` bigint(11) default NULL,
  `description` varchar(500) default NULL,
  `orderby` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

