ALTER TABLE t_user ADD COLUMN passwd VARCHAR(100) DEFAULT '' COMMENT '密码' AFTER username;