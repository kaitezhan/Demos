#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'create a interface'

import os
# step.1 create a proto
def create_proto(target,origin,package):
	tar_dir='./'+target
	if not os.path.exists(tar_dir):
		os.mkdir(tar_dir)
	tmp='tmp.proto'
	file = open('./'+target+'/'+tmp, 'w')
	try:
		file.write('syntax=\"proto3\";')
		file.write('\n')		
		file.write('option java_package = \"')
		file.write(package)
		file.write('\";\n')		
		ori_file = open(origin, 'r')
		try:
			for line in ori_file.readlines():
				file.write(line)			
		finally:
			ori_file.close()
	finally:
		file.close()
# step.2 execute	
	exc='protoc --java_out=./'+target+' ./'+target+'/'+tmp
	os.system(exc)
	
# =====================================================================
create_proto('java','from.proto','com.boneix.netty.proto')
create_proto('java','to.proto','com.boneix.netty.proto')