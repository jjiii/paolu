
DROP TABLE IF EXISTS sys_admin;
CREATE TABLE sys_admin
(
id bigint NOT NULL auto_increment,
realname varchar(50) NOT NULL,
username varchar(50) NOT NULL,
password varchar(50) NOT NULL,
is_locked tinyint NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
id bigint NOT NULL auto_increment,
parentId bigint,
name varchar(50) NOT NULL,
state varchar(3) DEFAULT NULL,
department varchar(50) NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sys_role_admin;
CREATE TABLE sys_role_admin (
  id bigint NOT NULL auto_increment,
  admin_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY  (id)
);

DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  id bigint NOT NULL auto_increment,
  parent_id bigint DEFAULT NULL,
  level smallint DEFAULT NULL COMMENT '菜单排列顺序',
  name varchar(50) DEFAULT NULL,
  types varchar(40) DEFAULT NULL COMMENT '资源类型',
  url varchar(200) DEFAULT NULL,
  icon varchar(100) DEFAULT NULL COMMENT '菜单图标',
  ishide tinyint DEFAULT '0'COMMENT '是否折叠隐藏',
  description varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint NOT NULL,
  `res_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`res_id`)
);





DROP TABLE IF EXISTS user_info;
CREATE TABLE user_info (
  id bigint NOT NULL auto_increment,
  real_name varchar(50) NOT NULL,
  user_name varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  head_img varchar(50),
  PRIMARY KEY (id)
);
