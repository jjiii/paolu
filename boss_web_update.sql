-- 版本更新权限资源脚本
-- 20170106 start
-- 订单管理 1.订单支付管理 2.退款订单管理
INSERT INTO `boss_menu` VALUES ('16', '0', '超级管理员', '2017-01-06 10:54:03', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '10', '', '04003', '订单支付管理', 'order/pay/reconcilelist');
INSERT INTO `boss_menu` VALUES ('17', '0', '超级管理员', '2017-01-06 10:55:46', '超级管理员', '2017-01-06 10:59:55', 'ACTIVE', NULL, 'YES', '2', '10', '', '04004', '退款订单管理', 'order/refund/reconcilelist');
INSERT INTO `boss_menu` VALUES ('18', '0', '超级管理员', '2017-01-14 17:15:00', '超级管理员', '2017-01-14 17:15:24', 'UNACTIVE', NULL, 'YES', '2', '8', '', '03005', '对账明细管理', 'billitem/list');
INSERT INTO `boss_menu` VALUES ('19', '0', '超级管理员', '2017-01-16 18:13:24', NULL, NULL, 'UNACTIVE', NULL, 'YES', '2', '1', '', '00105', '数据字典管理', 'dictionary/list');


INSERT INTO `boss_menu_role` VALUES ('34', NULL, 'admin', '2017-01-06 11:05:30', NULL, NULL, NULL, NULL, '1', '16');
INSERT INTO `boss_menu_role` VALUES ('35', NULL, 'admin', '2017-01-06 11:05:30', NULL, NULL, NULL, NULL, '1', '17');

INSERT INTO `boss_permission` VALUES ('45', '0', '超级管理员', '2017-01-06 11:02:45', NULL, NULL, 'ACTIVE', '订单对账确认平帐', '订单对账确认平帐', 'order:pay:reconcilesettle', '16');
INSERT INTO `boss_permission` VALUES ('46', '0', '超级管理员', '2017-01-06 11:03:10', NULL, NULL, 'ACTIVE', '订单支付对账查看帐户', '订单支付对账查看帐户', 'order:pay:reconcileview', '16');
INSERT INTO `boss_permission` VALUES ('47', '0', '超级管理员', '2017-01-06 11:04:13', NULL, NULL, 'ACTIVE', '支付对账获取列表数据', '支付对账列表', 'order:pay:reconcilelist', '16');
INSERT INTO `boss_permission` VALUES ('48', '0', '超级管理员', '2017-01-06 11:04:41', NULL, NULL, 'ACTIVE', '退款订单对账列表数据', '退款订单对账列表', 'order:refund:reconcilelist', '17');
INSERT INTO `boss_permission` VALUES ('49', '0', '超级管理员', '2017-01-06 11:05:01', NULL, NULL, 'ACTIVE', '退款对账确认平帐', '退款对账确认平帐', 'order:refund:reconcilesettle', '17');
INSERT INTO `boss_permission` VALUES ('50', '0', '超级管理员', '2017-01-06 11:05:15', NULL, NULL, 'ACTIVE', '退款对账查看帐户', '退款对账查看帐户', 'order:refund:reconcileview', '17');

INSERT INTO `boss_role_permission` VALUES ('64', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '45');
INSERT INTO `boss_role_permission` VALUES ('65', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '46');
INSERT INTO `boss_role_permission` VALUES ('66', NULL, '超级管理员', '2017-01-06 11:05:39', NULL, NULL, NULL, NULL, '1', '47');
INSERT INTO `boss_role_permission` VALUES ('67', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '48');
INSERT INTO `boss_role_permission` VALUES ('68', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '49');
INSERT INTO `boss_role_permission` VALUES ('69', NULL, '超级管理员', '2017-01-06 11:05:43', NULL, NULL, NULL, NULL, '1', '50');
-- 20170106 end

-- 20170323 start
-- 增加测试对账菜单
INSERT INTO `boss_menu` VALUES ('21', '0', '超级管理员', '2017-03-20 14:45:31', '超级管理员', '2017-03-20 14:51:11', 'ACTIVE', NULL, 'NO', '1', '0', 'icon-stack4', '005', '测试对账', '');
INSERT INTO `boss_menu` VALUES ('22', '0', '超级管理员', '2017-03-20 14:52:37', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '21', '', '005001', '修改支付订单', 'testbill/pay/list');
INSERT INTO `boss_menu` VALUES ('23', '0', '超级管理员', '2017-03-20 14:53:33', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '21', '', '005002', '修改退款订单', 'testbill/refund/list');
INSERT INTO `boss_menu` VALUES ('24', '0', '超级管理员', '2017-03-21 14:54:36', NULL, NULL, 'ACTIVE', NULL, 'YES', '2', '21', '', '005003', '修改第三方对账', 'testbill/uploadbillfile/view');

-- 菜单分配
INSERT INTO `boss_menu_role` VALUES ('21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '21');
INSERT INTO `boss_menu_role` VALUES ('22', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '22');
INSERT INTO `boss_menu_role` VALUES ('23', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '23');
INSERT INTO `boss_menu_role` VALUES ('24', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '24');

-- 增加权限
INSERT INTO `boss_permission` VALUES ('55', '0', '超级管理员', '2017-03-23 15:20:26', NULL, NULL, 'ACTIVE', '测试对账-支付订单-列表', '测试对账-支付订单-列表', 'test:payorder:list', '22');
INSERT INTO `boss_permission` VALUES ('56', '0', '超级管理员', '2017-03-23 15:20:47', NULL, NULL, 'ACTIVE', '测试对账-支付订单-修改', '测试对账-支付订单-修改', 'test:payorder:edit', '22');
INSERT INTO `boss_permission` VALUES ('57', '0', '超级管理员', '2017-03-23 15:21:38', '超级管理员', '2017-03-23 15:22:50', 'ACTIVE', '测试对账-退款订单-列表', '测试对账-退款订单-列表', 'test:refundorder:list', '23');
INSERT INTO `boss_permission` VALUES ('58', '0', '超级管理员', '2017-03-23 15:22:29', '超级管理员', '2017-03-23 15:23:05', 'ACTIVE', '测试对账-退款订单-修改', '测试对账-退款订单-修改', 'test:refundorder:edit', '23');
INSERT INTO `boss_permission` VALUES ('59', '0', '超级管理员', '2017-03-23 15:28:51', NULL, NULL, 'ACTIVE', '测试对账-第三方对账-查看', '测试对账-第三方对账-查看', 'test:bill:view', '24');
INSERT INTO `boss_permission` VALUES ('60', '0', '超级管理员', '2017-03-23 15:29:15', NULL, NULL, 'ACTIVE', '测试对账-第三方对账-上传文件', '测试对账-第三方对账-上传文件', 'test:bill:upload', '24');

-- 权限分配
INSERT INTO `boss_role_permission` VALUES ('55', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '55');
INSERT INTO `boss_role_permission` VALUES ('56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '56');
INSERT INTO `boss_role_permission` VALUES ('57', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '57');
INSERT INTO `boss_role_permission` VALUES ('58', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '58');
INSERT INTO `boss_role_permission` VALUES ('59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '59');
INSERT INTO `boss_role_permission` VALUES ('60', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '60');
-- 20170323 end