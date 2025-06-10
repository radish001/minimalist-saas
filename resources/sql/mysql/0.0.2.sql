-- 修改为mybatis flex框架后，deleted字段由 "0已删除 1未删除" 调整为 "0未删除  1已删除"
ALTER TABLE `minimalist`.`m_config` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_dept` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_dict` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_notice` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_perms` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_post` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_role` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;
ALTER TABLE `minimalist`.`m_user` MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除' AFTER `update_time`;



-- 租户表字段顺序、字段默认值、注释调整
ALTER TABLE m_tenant
MODIFY COLUMN `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注' AFTER `storage_id`,
MODIFY COLUMN `status` tinyint(0) NULL DEFAULT 1 COMMENT '状态 0禁用 1正常' AFTER `remark`,
MODIFY COLUMN `deleted` bit(1) NULL DEFAULT b'0' COMMENT '逻辑删除  0未删除  1已删除',
MODIFY COLUMN `version` int(0) NULL DEFAULT 0 COMMENT '版本号';

