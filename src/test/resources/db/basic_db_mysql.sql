
-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission (
id integer primary key not null auto_increment,
name varchar(1024) NOT NULL
);

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO sys_permission VALUES ('1', '/**=authc');
INSERT INTO sys_permission VALUES ('2', '/forword/basic/*.htm=anon ');
INSERT INTO sys_permission VALUES ('3', '/shiro/login.htm=anon');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
id integer primary key not null auto_increment,
name varchar(32)
);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO sys_role VALUES ('1', 'admin');
INSERT INTO sys_role VALUES ('2', 'gust');
INSERT INTO sys_role VALUES ('3', 'vip');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
id integer primary key not null auto_increment,
role_id integer,
permission_id integer
);

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO sys_role_permission VALUES ('1', '1', '1');
INSERT INTO sys_role_permission VALUES ('2', '2', '2');
INSERT INTO sys_role_permission VALUES ('3', '3', '3');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
id integer primary key not null auto_increment,
username varchar(32) not null,
password varchar(32) not null,
salt varchar(255)
);
ALTER TABLE `sys_user`
ADD UNIQUE INDEX `unique_username` (`username`) USING BTREE ;
-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO sys_user VALUES ('1', 'lzx', 'a237f81982f74291dd10f7dc115e271b', '132600921da3f9cc98e1600243ed2848');
INSERT INTO sys_user VALUES ('2', 'lsl', 'aa8ac14329ddcba5ffa20771ab1c467c', '9bda7986741b84e5655035ec53cd47f9');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS sys_user_info;
CREATE TABLE sys_user_info (
id integer primary key not null auto_increment,
user_id integer not null,
china_name varchar(32) not null,
simple_name varchar(32) not null,
deptment_id integer,
birthday date,
tel varchar(11),
sex integer,
address varchar(32),
area varchar(32)
);
ALTER TABLE `sys_user_info`
ADD UNIQUE INDEX `unique_user_id` (`user_id`) USING BTREE ;
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
id integer primary key not null auto_increment,
user_id integer,
role_id integer
);

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO sys_user_role VALUES ('1', '1', '1');
INSERT INTO sys_user_role VALUES ('2', '2', '2');
INSERT INTO sys_user_role VALUES ('3', '2', '3');
COMMIT;

-- ----------------------------
-- 费用报销单
-- ----------------------------
DROP TABLE IF EXISTS cost_reimburse;
CREATE TABLE cost_reimburse (
id integer primary key not null auto_increment,
cost_name varchar(32) not null,
user_id integer,
amount numeric(16,2),
create_time timestamp NULL DEFAULT NULL,
state varchar(32) DEFAULT NULL,
description varchar(1024)
);
-- ----------------------------
-- 费用项
-- ----------------------------
DROP TABLE IF EXISTS cost_item;
CREATE TABLE cost_item (
id integer primary key not null auto_increment,
item_name varchar(32) not null,
cost_id integer,
amount numeric(16,2),
category varchar(32) DEFAULT NULL
);
-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------