CREATE TABLE `init_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `current_value` int(20) NOT NULL DEFAULT '100000' COMMENT '��ǰ����ֵ',
  `sys_id`  int(20) NOT NULL  COMMENT 'ϵͳid',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT 'ϵͳ����',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='������ʼ����';


CREATE TABLE `sys_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT 'ϵͳ����',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='ϵͳ�û���';