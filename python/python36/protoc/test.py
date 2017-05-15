#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'create a interface'

import os
# util
def copy_content(tag,ori):
	ori_file = open(ori, 'r')
	try:
		for line in ori_file.readlines():
			tag.write(line)			
	finally:
		ori_file.close()
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
		copy_content(file,origin)
	finally:
		file.close()
# # step.2 execute
def exec_protoc(target,tmp):	
	exc='protoc --java_out=./'+target+' ./'+target+'/'+tmp
	os.system(exc)
# step.3 create interface
def intf_import(target,import_class):
	tmp = 'import.intf'
	import_str='import '+import_class+';'
	ori_file = open('./'+target+'/'+tmp, 'r')
	flag = False
	try:
		for line in ori_file.readlines():
			if line.strip()==import_str:
				flag=True
				break
	finally:
		ori_file.close()
	if not flag:
		file = open('./'+target+'/'+tmp, 'a')
		try:
			file.write(import_str)
			file.write('\n')
		finally:
			file.close()
def intf_body(target,req,resp,method):
	tmp='body.intf'
	file = open('./'+target+'/'+tmp, 'a')
	try:
		file.write('\t')
		file.write(resp)
		file.write(' ')		
		file.write(method)
		file.write(' (')	
		file.write(req)
		file.write(' ')		
		file.write('arg0')		
		file.write(');')
		file.write('\n')
	finally:
		file.close()
def intf_init(target,package,import_info,body_info,intf_name):
	tmp=intf_name+'.java'
	file = open('./'+target+'/'+tmp, 'w')
	try:
		file.write('package')
		file.write(' ')		
		file.write(package)
		file.write(';\n')
		copy_content(file,import_info)
		file.write('\n')
		file.write('public interface ')	
		file.write(intf_name)
		file.write(' {')
		file.write('\n')
		copy_content(file,body_info)
		file.write('}')		
		
	finally:
		file.close()
# =====================================================================
# create_proto('java','from.proto','com.boneix.netty.proto')
# create_proto('java','to.proto','com.boneix.netty.proto')
# intf_body('java/com/boneix/netty/intf','RichManProto.RichMan','RequestRichManProto.RequestRichMan','findOneRichMan')
# intf_import('java/com/boneix/netty/intf','com.mo9.bait.entity.GamePayUser')
# intf_init('java/com/boneix/netty/intf','com.boneix.netty.intf','java/com/boneix/netty/intf/import.intf','java/com/boneix/netty/intf/body.intf','UserInfo')