/*
Navicat PGSQL Data Transfer

Source Server         : localhost
Source Server Version : 90310
Source Host           : localhost:5432
Source Database       : basic_db
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90200
File Encoding         : 65001

Date: 2015-12-08 17:15:52
*/


-- ----------------------------
-- Sequence structure for sys_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_permission_id_seq";
CREATE SEQUENCE "sys_permission_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."sys_permission_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for sys_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_role_id_seq";
CREATE SEQUENCE "sys_role_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."sys_role_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for sys_role_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_role_permission_id_seq";
CREATE SEQUENCE "sys_role_permission_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
SELECT setval('"public"."sys_role_permission_id_seq"', 3, true);

-- ----------------------------
-- Sequence structure for sys_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_user_id_seq";
CREATE SEQUENCE "sys_user_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for sys_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_user_role_id_seq";
CREATE SEQUENCE "sys_user_role_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."sys_user_role_id_seq"', 1, true);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS "sys_permission";
CREATE TABLE "sys_permission" (
"id" int4 DEFAULT nextval('sys_permission_id_seq'::regclass) NOT NULL,
"name" varchar(1024) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO "sys_permission" VALUES ('1', '/**=authc');
INSERT INTO "sys_permission" VALUES ('2', '/forword/basic/*.htm=anon ');
INSERT INTO "sys_permission" VALUES ('3', '/shiro/login.htm=anon');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "sys_role";
CREATE TABLE "sys_role" (
"id" int4 DEFAULT nextval('sys_role_id_seq'::regclass) NOT NULL,
"name" varchar(32) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "sys_role" VALUES ('1', 'admin');
INSERT INTO "sys_role" VALUES ('2', 'gust');
INSERT INTO "sys_role" VALUES ('3', 'vip');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "sys_role_permission";
CREATE TABLE "sys_role_permission" (
"id" int4 DEFAULT nextval('sys_role_permission_id_seq'::regclass) NOT NULL,
"role_id" int4,
"permission_id" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO "sys_role_permission" VALUES ('1', '1', '1');
INSERT INTO "sys_role_permission" VALUES ('2', '2', '2');
INSERT INTO "sys_role_permission" VALUES ('3', '3', '3');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "sys_user";
CREATE TABLE "sys_user" (
"id" int4 DEFAULT nextval('sys_user_id_seq'::regclass) NOT NULL,
"username" varchar(32) COLLATE "default",
"password" varchar(32) COLLATE "default",
"salt" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "sys_user" VALUES ('1', 'lzx', 'a237f81982f74291dd10f7dc115e271b', '132600921da3f9cc98e1600243ed2848');
INSERT INTO "sys_user" VALUES ('2', 'lsl', 'aa8ac14329ddcba5ffa20771ab1c467c', '9bda7986741b84e5655035ec53cd47f9');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "sys_user_role";
CREATE TABLE "sys_user_role" (
"id" int4 DEFAULT nextval('sys_user_role_id_seq'::regclass) NOT NULL,
"user_id" int4,
"role_id" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "sys_user_role" VALUES ('1', '1', '1');
INSERT INTO "sys_user_role" VALUES ('2', '2', '2');
INSERT INTO "sys_user_role" VALUES ('3', '2', '3');
COMMIT;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
