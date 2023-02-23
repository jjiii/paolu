-- 以下表结构为初定版本，还根据具体业务修改

-- ------------------------------商户-------------------------------------------
drop table if exists merchant;
drop table if exists merchant_app;
drop table if exists merchant_app_config;

-- ------------------------------支付接口模块-----------------------------------
drop table if exists pay_payment_order;
drop table if exists pay_refund_order;
drop table if exists pay_trade_record;

-- ------------------------------通知-------------------------------------------
drop table if exists notify_record;
drop table if exists notify_record_log;

-- ------------------------------对账-------------------------------------------
drop table if exists bill_biz_summary;
drop table if exists bill_biz_batch;
drop table if exists bill_biz_file_notify;
drop table if exists bill_biz_doubt;
drop table if exists bill_biz_item;
drop table if exists bill_biz_mistake;

/*==============================================================*/
/* table: merchant     商户表                                                      */
/*==============================================================*/
create table merchant(
	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time         timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time           datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
	
	merchant_code		varchar(50) not null comment '公司ID',
	merchant_name		varchar(100) not null comment '公司名称',
	licence				varchar(100) comment '营业执照编号',
   	email				varchar(20)  comment '邮箱',
	mobile              varchar(20)  comment '手机号',
	remark				varchar(50) comment '备注',
	status				int comment '状态 0：关闭 1：开启',
	unique key key_merchant (merchant_code)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;


/*==============================================================*/
/* table: merchant_info     商户app                             */
/*==============================================================*/
create table merchant_app
(
   	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time         timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time           datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
	
   	
   	merchant_code       varchar(50) not null comment '商户编号',
   	merchant_name       varchar(100) not null comment '商户名称',
   	merchant_app_code	varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	varchar(50) not null comment '商户在本系统app名称',
   	
   	pri_key             varchar(1200) comment 'app接入本系统私钥',
   	pub_key             varchar(500) comment 'app接入公钥',
   	mct_pub_key         varchar(500) comment '商户公钥',
   	status              int(10) not null comment '状态 0：关闭 1：开启',
	download_notify_url 		varchar(200) comment '对账通知地址',
	handle_mistake_notify_url 	varchar(200) comment '对账错误通知地址'
	
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* table: merchant_pay_config     商户在第三方支付的配置信息          */
/*==============================================================*/
create table merchant_app_config
(
   	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time         timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time           datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
	
	merchant_code        varchar(50) not null comment '商户编号',
   	merchant_name        varchar(100) not null comment '商户名称',
	merchant_app_code	 varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '商户在本系统app名称',
   	channel         	 varchar(50) comment '渠道编号:支付宝：alipay,微信：weixin,银联:union',
   	channel_app_id       varchar(50) comment '支付宝、微信的app_id',
   	channel_merchant_id	 varchar(50) comment '微信、银联的商户id',
   	
   	cert_path			varchar(200) comment '微信、银联证书路径',
   	cert_pwd 			varchar(100) comment '微信、银联证书密码',
   	cert_validate		varchar(200) comment '银联验证证书目录',
   	sub_mch_id			varchar(50) comment '微信子商户ID',
	pri_key         	varchar(1200) comment '商户支付宝私钥',
   	pub_key       		varchar(500) comment '商户支付宝公钥',
   	cert_enc_path		varchar(200) comment '银联对敏感信息加密的证书路径',
   	status				int			 comment '状态 0：关闭 1：开启',
   	unique key key_merchant_app_config (merchant_app_code, channel)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;




/*==============================================================*/
/* table: notify_record     通知记录表                                                */
/*==============================================================*/
create table notify_record
(
	id					 bigint PRIMARY KEY AUTO_INCREMENT,
	creater              varchar(50) comment '创建者',
	create_time          timestamp not null default current_timestamp comment '创建时间',
	editor               varchar(50) comment '修改人',
	edit_time            datetime default null  comment '修改时间',
	version              int not null default 0 comment '版本号',
	
	channel         	 varchar(50) comment '渠道编号:支付宝：alipay,微信：weixin,银联:union',
	pay_type			 varchar(10) comment '支付订单，还是退款订单：支付:pay,退款:refund',
	merchant_app_code	 varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '商户在本系统app名称',
   	merchant_order_no    varchar(50) not null comment '商户的订单号',
   	order_no             varchar(50) comment '系统生成的支付流水号',
   	refund_no		 	 varchar(50) default '0' comment '系统生成退款订单号',
   
	notify_id            varchar(50) not null comment '通知记录ID',
	notify_type          varchar(20) comment '通知类型',
   	notify_times         int not null default 1 comment '通知次数',
   	notify_times_limit   int not null default 5 comment '最大通知次数',
   	url                  varchar(2000) not null comment '通知地址',
	
   	request             varchar(2000) not null comment '请求参数',
   	response            varchar(1000) not null comment '接收参数',
   	status              int(10) not null default 0 comment '1:成功 0:失败',
   	
   	unique key key_notify_record (order_no, refund_no)

)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

/*==============================================================*/
/* table: notify_record     通知日志表                                                */
/*==============================================================*/
/*create table notify_record_log
(
   	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time          timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time            datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
   	notify_id           varchar(50) not null comment '通知记录ID',
   	request             varchar(2000) not null comment '请求参数',
   	response            varchar(2000) not null comment '接受参数',
   	merchant_app_code	 varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '商户在本系统app名称',
   	merchant_order_no   varchar(50) not null comment '商户的订单号',
   	http_status         varchar(50) not null comment '收到的http状态'
	
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;
*/

/*==============================================================*/
/*  table: pay_payment_order 支付订单表                                              */
/*==============================================================*/
create table pay_payment_order
(
   	id					 bigint PRIMARY KEY AUTO_INCREMENT,
	creater              varchar(50) comment '创建者',
	create_time          timestamp not null default current_timestamp comment '创建时间',
	editor               varchar(50) comment '修改人',
	edit_time            datetime default null  comment '修改时间',
	version              int not null default 0 comment '版本号',
	
	merchant_code        varchar(50) not null comment '商户编号',
   	merchant_name        varchar(100) not null comment '商户名称',
	merchant_app_code	 varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '商户在本系统app名称',
   	channel_app_id       varchar(50) comment '支付宝、微信的app_id',
   	channel_merchant_id	 varchar(50) comment '微信、银联的商户id',
   	
	order_no             varchar(50) comment '系统生成的支付流水号',
	merchant_order_no    varchar(50) not null comment '商户订单号',
	product_name         varchar(300) not null comment '商品名称',
    channel         	 varchar(50) comment '渠道编号:支付宝：alipay,微信：weixin,银联:union',
    pay_way        	 	 varchar(50) comment '自定义支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)',
   	amount         		 decimal(20,2) not null comment '订单金额',
   	return_url           varchar(300) comment '页面回调通知url',
   	notify_url           varchar(300) comment '后台异步通知url',
   	order_time           datetime  not null comment '收到订单时间',
   	order_ip             varchar(50) comment '下单ip',
   	remark               varchar(500) comment '备注信息',
   	
   	status               varchar(20) default 'pay_wait' comment '关闭(超时\未收到通知):close, 待支付:pay_wait, 成功:success, 结束(不能退款):finish',
   	close_reason         varchar(500) comment '关闭原因',
   	success_reason         varchar(500) comment '成功原因',
   	finish_time		     datetime comment '完成时间，即关闭或成功的时间。(结束为不可退款状态，该订单已经完成，无需再记录)',
   	trade_no	 		 varchar(64) comment '第三方返回流水号',
   	buyer_id			 varchar(50) comment '第三方返回付款买家ID',
	time_out		     int default  60 comment '超时时间(单位分钟)',
   	time_expire          datetime comment '到期时间',
   	bill_status			 varchar(50) default 'wait' comment '对账状态,见枚举类BillStatusEum',
    unique key key_pay_payment_order (order_no)
    
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

/*==============================================================*/
/*  table: pay_refund_order  退款订单表                                              */
/*==============================================================*/
create table pay_refund_order
(
   	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time          timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time           datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
	
	merchant_code        varchar(50) not null comment '原商户编号',
   	merchant_name        varchar(100) not null comment '原商户名称',
	merchant_app_code	 varchar(50) not null comment '原商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '原商户在本系统app名称',
   	channel_app_id       varchar(50) comment '原支付宝、微信的app_id',
   	channel_merchant_id	 varchar(50) comment '原微信、银联的商户id',
	
	order_no             varchar(50) comment '原支付流水号',
	merchant_order_no    varchar(50) not null comment '原商户订单号',
	product_name         varchar(300) not null comment '原商品名称',
    channel         	 varchar(50) comment '原渠道编号:支付宝：alipay,微信：weixin,银联:union',
    pay_way        	     varchar(50) comment '原支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)',
   	amount         		 decimal(20,2) not null comment '原订单金额',
   	order_time           datetime not null comment '原订单时间',
   	remark               varchar(200) comment '原备注信息',
   	status               varchar(20) comment '原订单状态：close, wait, success, finish',
   	finish_time		     datetime comment '原订成功或关闭的成时间',
   	trade_no	 		 varchar(64) comment '原第三方返回流水号',
   	buyer_id			 varchar(50) comment '原第三方返回付款买家ID',
   	
   	refund_no		 	 varchar(50) comment '系统生成退款订单号',
   	refund_merchant_no   varchar(50) not null comment '商户退款订单号，(修改成不用，因为商户订单可以为重复)',
   	refund_reason        varchar(500) comment '退款原因',
   	refund_amount        decimal(20,2) not null comment '退款金额',
   	refund_time			 datetime not null comment '申请退款时间',
   	refund_status		 varchar(20) default 'application' comment '向第三方申请退款中:application, 成功退款:success, 退款失败:faile ',
   	refund_faile_reason         varchar(500) comment '失败原因',
   	refund_success_reason         varchar(500) comment '成功原因',
   	refund_finish_time	 datetime comment '完成时间，即退款成功或退款失败时间',
	refund_buyer_id		 varchar(50) comment '实际退款到账的买家ID',
	refund_trade_no	 	 varchar(64) comment '第三方返回流水号',
	refund_remark        varchar(200) comment '退款备注',
	bill_status			 varchar(50) default 'wait' comment '对账状态,见枚举类BillStatusEum',
    unique key key_pay_refund_order (refund_merchant_no, merchant_app_code)
    
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

/*==============================================================*/
/*  table: pay_trade_record  交易记录表,金额有变动的退款、支付全部记录*/
/*==============================================================*/
create table pay_trade_record
(
   	id					bigint PRIMARY KEY AUTO_INCREMENT,
	creater             varchar(50) comment '创建者',
	create_time         timestamp not null default current_timestamp comment '创建时间',
	editor              varchar(50) comment '修改人',
	edit_time           datetime default null  comment '修改时间',
	version             int not null default 0 comment '版本号',
	
	merchant_code        varchar(50) not null comment '商户编号',
   	merchant_name        varchar(100) not null comment '商户名称',
	merchant_app_code	 varchar(50) not null comment '商户在本系统的appID',
   	merchant_app_name	 varchar(50) not null comment '商户在本系统app名称',
   	channel_app_id       varchar(50) comment '支付宝、微信的app_id',
   	channel_merchant_id	 varchar(50) comment '微信、银联的商户id',
	
	order_no             varchar(50) comment '支付流水号',
	merchant_order_no    varchar(50) not null comment '商户订单号',
	product_name         varchar(300) not null comment '商品名称',
    channel         	 varchar(50) comment '渠道编号:支付宝：alipay,微信：weixin,银联:union',
    pay_way         	 varchar(50) comment '支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)',
    amount         		 decimal(20,2) not null comment '第三方实际到账的订单金额',
   	order_time           datetime not null comment '收到订单时间',
   	remark               varchar(200) comment '备注信息',
   	trade_no	 		 varchar(64) comment '第三方返回流水号',
   	buyer_id			 varchar(50) comment '付款买家ID',
   	finish_time		 datetime comment '收到第三方通知成功的时间，或者查询第三方时状态为成功的时间',
   	 	
   	refund_no		 	 varchar(50) default '0' comment '系统生成退款订单号',
   	refund_merchant_no   varchar(50) comment '商户退款订单号',
   	refund_reason        varchar(500) comment '退款原因',
   	refund_amount        decimal(20,2) comment '退款金额',
   	refund_time			 datetime comment '申请退款时间',
   	refund_success_time	 datetime comment '退款收到第三方通知成功的时间，或者查询第三方时状态为成功的时间',
	refund_buyer_id		 varchar(50) comment '实际退款到账的买家ID',
	refund_trade_no	 	 varchar(64) comment '第三方返回的退款流水号',
	refund_remark        varchar(200) comment '退款备注',
	notifiy_param		 varchar(2000) comment '第三方通知的参数记录',
    key key_pay_trade_record (merchant_order_no, refund_merchant_no),
    unique key key_u_pay_trade_record (order_no,refund_no)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;




/*==============================================================*/
/*  table: bill_biz_summary  对账汇总表 */
/*==============================================================*/
CREATE TABLE `bill_biz_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `batch_count` int(8) NOT NULL DEFAULT '0' COMMENT '对账批次总数',
  `batch_run_success_count` int(8) NOT NULL DEFAULT '0' COMMENT '对账批次成功个数',
  `merchant_app_count` int(8) NOT NULL DEFAULT '0' COMMENT '商户应用总数',
  `bill_make_success_count` int(8) NOT NULL DEFAULT '0' COMMENT '生成账单成功总数',
  `download_notify_success_count` int(8) NOT NULL DEFAULT '0' COMMENT '下载通知成功总数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_biz_summary_bill_date` (`bill_date`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='业务对账汇总表';

/*==============================================================*/
/*  table: bill_biz_batch  对账批次表 */
/*==============================================================*/
CREATE TABLE `bill_biz_batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `pay_channel` varchar(30) NOT NULL COMMENT '支付渠道：微信、支付宝、银联',
  `channel_app_id` varchar(64) NOT NULL DEFAULT '' COMMENT '支付渠道应用ID：公众号ID、支付宝应用ID',
  `channel_merchant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '支付渠道商户号',
  `handle_status` varchar(30) NOT NULL DEFAULT '' COMMENT '批次处理状态：初始化、进行中、已完成、已中断',
  `handle_remark` varchar(1000) NOT NULL DEFAULT '' COMMENT '批次处理状描述',
  `start_time` datetime DEFAULT NULL COMMENT '启动时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `restart_count` int(8) NOT NULL DEFAULT '0' COMMENT '重启次数',
  `channel_bill_store_path` varchar(500) NOT NULL DEFAULT '' COMMENT '支付渠道对账单zip文件存储路径',
  `trade_count` int(11) NOT NULL DEFAULT '0' COMMENT '支付系统支付订单总笔数',
  `trade_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支付系统支付订单总金额',
  `refund_count` int(11) NOT NULL DEFAULT '0' COMMENT '支付系统退款订单总笔数',
  `refund_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支付系统退款订单总金额',
  `channel_trade_count` int(11) NOT NULL DEFAULT '0' COMMENT '支付渠道支付订单总笔数',
  `channel_trade_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支付渠道支付订单总金额',
  `channel_refund_count` int(11) NOT NULL DEFAULT '0' COMMENT '支付渠道退款订单总笔数',
  `channel_refund_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '支付渠道退款订单总金额',
  `mistake_count` int(8) NOT NULL DEFAULT '0' COMMENT '差错总数量',
  `mistake_unhandle_count` int(8) NOT NULL DEFAULT '0' COMMENT '未处理差错数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_biz_batch_bpccid` (`bill_date`,`pay_channel`,`channel_merchant_id`,`channel_app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='业务对账批次表';


/*==============================================================*/
/*  table: bill_biz_file_notify  对账文件通知表 */
/*==============================================================*/
CREATE TABLE `bill_biz_file_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `merchant_code` varchar(64) NOT NULL DEFAULT '' COMMENT '商户编号',
  `merchant_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商户名称',
  `merchant_app_code` varchar(64) NOT NULL DEFAULT '' COMMENT '商户应用编号',
  `merchant_app_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商户应用名称',
  `file_path` varchar(500) NOT NULL DEFAULT '' COMMENT '商户应用对账单zip文件存储路径',
  `file_status` varchar(30) NOT NULL DEFAULT '' COMMENT '文件操作状态：成功,失败',
  `file_remark` varchar(30) NOT NULL DEFAULT '' COMMENT '文件生成操作描述',
  `notify_url` varchar(255) NOT NULL DEFAULT '' COMMENT '账单下载通知URL',
  `notify_status` varchar(30) NOT NULL DEFAULT '' COMMENT '文件操作状态：成功,失败',
  `notify_remark` varchar(1000) NOT NULL DEFAULT '' COMMENT '文件生成操作描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_biz_file_notify_bdac` (`bill_date`,`merchant_app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='对账文件通知表';


/*==============================================================*/
/*  table: bill_biz_doubt  对账存疑表 */
/*==============================================================*/
CREATE TABLE `bill_biz_doubt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `pay_channel` varchar(30) DEFAULT '' COMMENT '支付渠道：微信、支付宝、银联',
  `pay_way` varchar(30) DEFAULT '' COMMENT '支付方式：扫码支付、APP支付、公众号支付',
  `channel_app_id` varchar(64) DEFAULT '' COMMENT '支付渠道应用ID：公众号ID、支付宝应用ID',
  `channel_merchant_id` varchar(64) DEFAULT '' COMMENT '支付渠道商户号',
  `bill_type` varchar(30) DEFAULT '' COMMENT '对账类型（订单类型）当日成功支付订单、当日退款订单',
  `merchant_code` varchar(64) DEFAULT '' COMMENT '商户编号',
  `merchant_name` varchar(200) DEFAULT '' COMMENT '商户名称',
  `merchant_app_code` varchar(64) DEFAULT '' COMMENT '商户系统编号',
  `merchant_app_name` varchar(200) DEFAULT '' COMMENT '商户系统名称',
  `merchant_commodity` varchar(200) DEFAULT '' COMMENT '商户系统产品',
  `currency` varchar(20) DEFAULT 'CNY' COMMENT '货币种类：人民币CNY',
  `pay_merch_order_no` char(64) DEFAULT '' COMMENT '商户支付订单号，来自商户应用系统订单号',
  `pay_order_no` char(64) DEFAULT '' COMMENT '支付订单号',
  `pay_trade_no` char(64) DEFAULT '' COMMENT '支付流水号',
  `pay_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付订单金额',
  `pay_trade_status` varchar(30) DEFAULT '' COMMENT '支付订单状态',
  `pay_order_time` datetime DEFAULT NULL COMMENT '支付订单生成时间',
  `pay_success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `refund_merch_order_no` char(64) DEFAULT '' COMMENT '商户退款订单号，来自商户应用系统订单号',
  `refund_order_no` char(64) DEFAULT '' COMMENT '退款订单号',
  `refund_trade_no` char(64) DEFAULT '' COMMENT '退款流水号',
  `refund_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '退款订单金额',
  `refund_trade_status` varchar(30) DEFAULT '' COMMENT '退款订单状态',
  `refund_apply_time` datetime DEFAULT NULL COMMENT '退款订单申请时间',
  `refund_success_time` datetime DEFAULT NULL COMMENT '退款订单成功时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='业务对账存疑表';

/*==============================================================*/
/*  table: bill_biz_mistake  对账差错表 */
/*==============================================================*/
CREATE TABLE `bill_biz_mistake` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `pay_channel` varchar(30) DEFAULT '' COMMENT '支付渠道：微信、支付宝、银联',
  `pay_way` varchar(30) DEFAULT '' COMMENT '支付方式 扫码支付、APP支付、公众号支付',
  `channel_app_id` varchar(64) DEFAULT '' COMMENT '支付渠道应用ID：公众号ID、支付宝应用ID',
  `channel_merchant_id` varchar(64) DEFAULT '' COMMENT '支付渠道商户号',
  `merchant_code` varchar(64) DEFAULT '' COMMENT '商户编号',
  `merchant_name` varchar(200) DEFAULT '' COMMENT '商户名称',
  `merchant_app_code` varchar(64) DEFAULT '' COMMENT '商户系统编号',
  `merchant_app_name` varchar(200) DEFAULT '' COMMENT '商户系统名称',
  `merchant_commodity` varchar(200) DEFAULT '' COMMENT '商户产品',
  `currency` varchar(20) DEFAULT 'CNY' COMMENT '货币种类：人民币CNY',
  `bill_type` varchar(30) DEFAULT '' COMMENT '对账类型：当日成功支付订单、当日退款订单',
  `pay_merch_order_no` char(64) DEFAULT '' COMMENT '商户支付订单号，来自商户应用系统订单号',
  `pay_order_no` char(64) DEFAULT '' COMMENT '支付系统支付订单号',
  `pay_trade_no` char(64) DEFAULT '' COMMENT '支付系统支付流水号',
  `pay_trade_status` varchar(30) DEFAULT '' COMMENT '支付系统支付订单状态',
  `pay_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付系统支付订单金额',
  `pay_order_time` datetime DEFAULT NULL COMMENT '支付系统支付订单生成时间',
  `pay_success_time` datetime DEFAULT NULL COMMENT '支付系统支付成功时间',
  `channel_order_no` char(64) DEFAULT '' COMMENT '支付渠道支付订单号',
  `channel_trade_no` char(64) DEFAULT '' COMMENT '支付渠道支付流水号',
  `channel_trade_status` varchar(30) DEFAULT '' COMMENT '支付渠道支付订单状态',
  `channel_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付渠道支付订单金额',
  `channel_trade_order_time` datetime DEFAULT NULL COMMENT '支付渠道支付时间',
  `channel_trade_success_time` datetime DEFAULT NULL COMMENT '支付渠道支付时间',
  `refund_merch_order_no` char(64) DEFAULT '' COMMENT '商户退款订单号，来自商户应用系统订单号',
  `refund_order_no` char(64) DEFAULT '' COMMENT '支付系统退款订单号',
  `refund_trade_no` char(64) DEFAULT '' COMMENT '支付系统退款流水号',
  `refund_trade_status` varchar(30) DEFAULT '' COMMENT '支付系统退款订单状态',
  `refund_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付系统退款订单金额',
  `refund_apply_time` datetime DEFAULT NULL COMMENT '支付系统退款订单申请时间',
  `refund_success_time` datetime DEFAULT NULL COMMENT '支付系统退款订单成功时间',
  `channel_refund_order_no` char(64) DEFAULT '' COMMENT '支付渠道退款订单号',
  `channel_refund_trade_no` char(64) DEFAULT '' COMMENT '支付渠道退款流水号',
  `channel_refund_trade_status` varchar(30) DEFAULT '' COMMENT '支付渠道退款订单状态',
  `channel_refund_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付渠道退款订单金额',
  `channel_refund_apply_time` datetime DEFAULT NULL COMMENT '支付系统退款订单申请时间',
  `channel_refund_success_time` datetime DEFAULT NULL COMMENT '支付系统退款订单成功时间',
  `mistake_type` varchar(30) DEFAULT '' COMMENT '差错类型',
  `handle_status` varchar(30) DEFAULT '' COMMENT '差错处理状态',
  `handle_way` varchar(30) DEFAULT '' COMMENT '差错处理方式:以支付渠道为准、以支付系统为准',
  `handle_remark` varchar(1000) DEFAULT '' COMMENT '差错处理备注 操作员填入处理说明',
  `handle_user` varchar(64) DEFAULT '' COMMENT '差错处理操作员ID',
  `handle_time` datetime DEFAULT NULL COMMENT '差错处理操作时间',
  `notify_status` varchar(30) DEFAULT '' COMMENT '商户应用通知状态:已通知商户应用、商户应用已接收',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='业务对账差错表';


/*==============================================================*/
/*  table: bill_biz_item  账单明细表 */
/*==============================================================*/
CREATE TABLE `bill_biz_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `creater` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `editor` varchar(64) DEFAULT NULL COMMENT '修改者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `bill_date` date NOT NULL COMMENT '账单日期',
  `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
  `merchant_code` varchar(64) DEFAULT '' COMMENT '商户编号',
  `merchant_name` varchar(200) DEFAULT '' COMMENT '商户名称',
  `merchant_app_code` varchar(64) DEFAULT '' COMMENT '商户系统编号',
  `merchant_app_name` varchar(200) DEFAULT '' COMMENT '商户系统名称',
  `currency` varchar(20) DEFAULT 'CNY' COMMENT '货币种类：人民币CNY',
  `merchant_commodity` varchar(200) DEFAULT '' COMMENT '商户系统产品',
  `pay_channel` varchar(30) DEFAULT '' COMMENT '支付渠道：微信、支付宝、银联',
  `pay_way` varchar(30) DEFAULT '' COMMENT '支付方式：扫码支付、APP支付、公众号支付',
  `channel_app_id` varchar(64) DEFAULT '' COMMENT '支付渠道应用ID：公众号ID、支付宝应用ID',
  `channel_merchant_id` varchar(64) DEFAULT '' COMMENT '支付渠道商户号',
  `bill_type` varchar(30) DEFAULT '' COMMENT '对账类型（订单类型）当日成功支付订单、当日退款订单',
  `pay_merch_order_no` char(64) DEFAULT '' COMMENT '商户支付订单号，来自商户应用系统订单号',
  `pay_order_no` char(64) DEFAULT '' COMMENT '支付系统支付订单号',
  `pay_trade_no` char(64) DEFAULT '' COMMENT '支付系统支付流水号',
  `pay_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付系统支付订单金额',
  `pay_order_time` datetime DEFAULT NULL COMMENT '支付系统支付订单生成时间',
  `pay_success_time` datetime DEFAULT NULL COMMENT '支付订单支付时间',
  `refund_merch_order_no` char(64) DEFAULT '' COMMENT '商户退款订单号，来自商户应用系统订单号',
  `refund_order_no` char(64) DEFAULT '' COMMENT '支付系统退款订单号',
  `refund_trade_no` char(64) DEFAULT '' COMMENT '支付系统退款流水号',
  `refund_trade_amount` decimal(20,2) DEFAULT NULL COMMENT '支付系统退库订单金额',
  `refund_apply_time` datetime DEFAULT NULL COMMENT '支付系统退款订单申请时间',
  `refund_success_time` datetime DEFAULT NULL COMMENT '支付系统退款订单成功时间',
  `source` varchar(30) DEFAULT '' COMMENT '数据来源：',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务对账明细表';



INSERT INTO merchant (id,creater,create_time,editor,edit_time,version,
merchant_code,merchant_name,licence,email,mobile,remark) VALUES
(1,'系统',now(),null,null,0, 	'1','枫车电子商务有限责任公司',null,null,null,'枫车汽配有限公司');

INSERT INTO merchant_app (id,creater,create_time,editor,edit_time,version,
merchant_code,merchant_name,merchant_app_code,merchant_app_name,pri_key,pub_key,mct_pub_key,status) VALUES
(1,'系统',now(),null,null,0, 	'1','枫车电子商务有限责任公司','1481083760950','商城',
'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALK5gcD0ehR4Zh4RGTpNZq3AtVPblXYcOXJxPOn1MzUiEJw3yvhgesQ0tmNzBRq74maJpfrm400SL53uRlP3/nJfY3OW2pfyjzoy57bG5tLTgaiwxtLuyaB07ghJzls4mkxUFzUztUHIBCff0eiKUmFXTCVUJGNsC/SOcqlNz6M5AgMBAAECgYEAmetLRNsHnEOIZpKBMIXiTPdu8lZk5MAv9VByjma+gB7jQTaHldq5P+rJvOIc3kY7F+WpzWg1D3X/Djtb1Ar61SKpamVDqOD/LavwbwkbCR+E7eamzGSu/GgWzTQ1oLzGxakfvfBt5nAcZKqHe8LGd8umCVZJGDKoVulTpSLHVAECQQDbIMcN5955E0pDLkvHdWY7MVdF9xU9z+AJOCLzKWbx551hUpEBbA46ta2ZDX5mSjI/vu/eEIWD0shnJLFpHnWBAkEA0MxM/d6SWwkgV0rOQ5CHcHegi2pgcaoYWFsP+V0b2lnmQLCWtyyEpVi5jfIJuoDLgoy8uBxtlNYeetmNfiM5uQJAcnQ5ZEsDCeyIcnShAiqQ3kQUWKgJAUMtusMGhknOynV235mXwc1l1UaFyRaiOd1xR5h8g1nP0x3qrO0eeVB+gQJBAMbAssk2BLsDhPWTD6Tg/wvf08LiD8wizenNRPdp2gmPac5KSi0zA1Ehk/+6VehikVZEAaB/7w+ugnUOgdGAVoECQFRg8tSJU7Wb8UD1h1IC+jnYT8uLGTbqs3JZmXMifrqkkZm1OGx1uyp6I6aMrIbKjKP9FPAB48547D9SGkTaDeA=',

'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyuYHA9HoUeGYeERk6TWatwLVT25V2HDlycTzp9TM1IhCcN8r4YHrENLZjcwUau+JmiaX65uNNEi+d7kZT9/5yX2NzltqX8o86Mue2xubS04GosMbS7smgdO4ISc5bOJpMVBc1M7VByAQn39HoilJhV0wlVCRjbAv0jnKpTc+jOQIDAQAB',
'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPPda9eG+2WvE0M12vJ5Uk0Euy4ZO8y06dYw1dlUKSUb71JPE/IJvIjWVKpgFzhgWw91y7w+CBSEeCn2+08WElHOSPUheuxsi03zQ6PZ1mQNHfyWO/ktkTJzXnEfM88FUWfbPj56H3aTtctW3nK2Sorr0wgsjTP+y8j6MtYwawUQIDAQAB',
1);

INSERT INTO merchant_app_config(id,creater,create_time,editor,edit_time,version,
merchant_code,merchant_name,merchant_app_code,merchant_app_name,channel,channel_app_id,channel_merchant_id,cert_path,cert_pwd,cert_validate,sub_mch_id,pri_key,pub_key,cert_enc_path) VALUES
(1,'系统',now(),null,null,0, 	
'1','枫车电子商务有限责任公司',1481083760950,'商城','alipay','2016080100144012',null,null,null,null,null,
'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJrX943qCctdyB+3KQEH2yTqgZdTlP4Q+WsfXezVaE2MmVquGsmQ2V3F1KZYIpYBpdO2pnO3D5IBMMTzmX87W2hxNVZM8Vx/WZ+lDyZvyPiyfBQtcMDafy/ceb6evjZdnsU2ks91d9mUc12HzTTez2ZtBwS1UiXLFprIFR4sG2glAgMBAAECgYBM00JcAvaBFDKqqbGKdV9hXYiWkD2oILvfTlzHmMp7T3r25tMbluaCBBmRvsDKNF8WP9UuLHFpO3X7AfHyknyy+xGKGPA5gssIvaOk/lWhyqv4xsxggUXKT49aY3SF7hsCkrIPJm7sNd2hNKnPeYFdPva8LzLDTsJy6AvdCfQ/MQJBANUluv8Mu5yLFC5x78K9M/I/KMQa8DuHpCjVlNrasSd1yFg4sitgg1l594EHPDcsJTGzCRrVORozn0r8JK/i+p8CQQC5+XVeQDA/AvuiGg8zXvhNHsEgjDUZfCmf2d+cR7e0T3dD6Mp+GZVdshN3pinBrNaYu+nrzyGF2r/y41W2J+q7AkB8Jp72AlehBg16RBkwZ/5C4vD+0OYO9qHyuv0aQPmhD2Tjphp5U50OWBGHEUzMoiUD/tGV1I6PKXRmO9murVnnAkBvWq/tHFAHGrki6amaX84bF0QaQfl1ZgPiY+lhQQv9GevWrKe6c4UdEghYBxVPkzb3QuUgvehbpoxyWa6zoBkLAkB2EK7dpIozIy5Y1TEiqnwI9rPlTdcwVqauC+Vf6E5HBTB4niZTw074hdaPyW4zcXA3ZIZzJ8f7fP80869F8SMn',
'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB',
null
),
(2,'系统',now(),null,null,0, 	
'1','枫车电子商务有限责任公司',1481083760950,'商城','unionpay',null,'777290058110048','unionpay/700000000000001/acp_test_sign.pfx','000000','unionpay/700000000000001/',null,
null,
null,
'unionpay/700000000000001/acp_test_enc.cer'
);


ALTER TABLE merchant_app_config ADD `app_secret` varchar(200) default '' COMMENT '微信js认证需要';
ALTER TABLE merchant_app_config ADD `partner_id` varchar(200) default '' COMMENT '微信app认证需要';
ALTER TABLE merchant_app_config ADD `pay_key` varchar(200) default '' COMMENT '微信支付key';

ALTER TABLE merchant_app_config ADD `pay_way` varchar(50) default 'web' COMMENT '自定义支付类型编号(包括支付宝、微信、银联所有支付方式.具体见枚举类)';
ALTER TABLE merchant_app_config drop index `key_merchant_app_config`;
ALTER TABLE merchant_app_config ADD unique key `key_merchant_app_config` (merchant_app_code, channel, pay_way);