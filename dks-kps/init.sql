CREATE TABLE `init_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `current_value` int(20) NOT NULL DEFAULT '100000' COMMENT '当前主键值',
  `sys_id`  int(20) NOT NULL  COMMENT '系统id',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT '系统名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='主键初始化表';


CREATE TABLE `sys_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT '系统名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统用户表';