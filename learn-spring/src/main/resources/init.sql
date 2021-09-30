CREATE TABLE `develop_task` (
                                `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                `name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '开发任务名称',
                                `app_key` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '开发任务appKey',
                                `app_secret` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '开发任务appSecret',
                                `project_number` int(11) NOT NULL COMMENT '最大支持项目数量',
                                `customer` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '客户',
                                `remark` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
                                `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `app_index` (`app_key`,`app_secret`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='开发任务';


CREATE TABLE `develop_task_project` (
                                        `project_id` bigint(20) NOT NULL COMMENT '项目ID',
                                        `task_id` int(11) NOT NULL COMMENT '开发任务ID',
                                        UNIQUE KEY `project_develop_index` (`project_id`,`task_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='开发任务和项目关联关系表';