#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'create a interface'

# =====================================================================
# step.1 create a proto
file = open('test.proto', 'w')
try:
	file.write('syntax=\"proto3\";'+'\n')
	file.write('option java_package = \"com.boneix.netty.proto\";'+'\n')
	ori_file = open('from.proto', 'r')
	try:
		for line in ori_file.readlines():
			 file.write(line)			
	finally:
		ori_file.close()
finally:
     file.close()
# =====================================================================
# step.2 execute
import os
exc='protoc.exe --java_out=./ ./test.proto'
os.system(exc)

# =====================================================================