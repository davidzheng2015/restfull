
/* Drop Tables 

DROP TABLE FW_DEP;
DROP TABLE FW_MENU;
DROP TABLE FW_ROLE;
DROP TABLE FW_USER;
DROP TABLE FW_USERROLE;
DROP TABLE MSG_INFO;
DROP TABLE MSG_TEXT;*/




/* Create Tables */

CREATE TABLE FW_DEP
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	NAME varchar(20) COMMENT '部门名称',
	PRIMARY KEY (ID)
) COMMENT = '部门表';


CREATE TABLE FW_MENU
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	PID int unsigned COMMENT '上级ID',
	NAME varchar(20) COMMENT '名称',
	URL varchar(255) COMMENT 'URL',
	ORDER_NO int unsigned COMMENT '排序号',
	-- 用，分割
	PATH varchar(50) COMMENT '层级路径',
	VISBLE char(1) COMMENT '是否可见',
	PRIMARY KEY (ID)
) COMMENT = '基本框架_菜单';


CREATE TABLE FW_ROLE
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	NAME varchar(20) COMMENT '角色名',
	res_desc varchar(1000) COMMENT 'res_desc',
	PRIMARY KEY (ID)
) COMMENT = '角色表';


CREATE TABLE FW_USER
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	USERNAME varchar(20) COMMENT '用户名',
	PASSWORD char(64) COMMENT '密码',
	SIGNUP_TIME datetime COMMENT '注册时间',
	LASTACT_TIME datetime COMMENT '最后活跃时间',
	IP char(15) COMMENT 'IP地址',
	YXBZ char(1) COMMENT '有效标志',
	DEPID int COMMENT '部门ID',
	cname varchar(200) COMMENT '中文名称',
	PRIMARY KEY (ID)
) COMMENT = '基本框架_用户表';


CREATE TABLE FW_USERROLE
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	ROLEID int COMMENT '角色ID',
	USERID char(32) COMMENT '用户ID',
	PRIMARY KEY (ID)
) COMMENT = '用户角色对应表';


CREATE TABLE MSG_INFO
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	RECID int unsigned COMMENT '接收人ID',
	SENTID int unsigned COMMENT '发送者ID',
	-- 0 删除
	-- 1 未读
	-- 2 已读
	STATUS tinyint COMMENT '状态',
	TEXTID int unsigned COMMENT '内容ID',
	CREATE_TIME datetime COMMENT '建立时间',
	EXPIRED_TIME datetime COMMENT '过期时间',
	PRIMARY KEY (ID)
) COMMENT = '消息表';


CREATE TABLE MSG_TEXT
(
	ID int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
	CONTENT text COMMENT '内容',
	TITLE varchar(50) COMMENT '标题',
	PRIMARY KEY (ID)
) COMMENT = '消息内容表';



