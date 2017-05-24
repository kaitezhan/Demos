#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'create a interface'

import os
import shutil
# step.1 组合版本
def jar_init(oriDir,pom_path,version):
	curPath=os.path.abspath('.')
	tmp=os.path.join(curPath,'tmp/demo')	
	if os.path.exists(tmp):
		shutil.rmtree(tmp)
	tagPath=os.path.join(tmp,'src/main')
	os.makedirs(tagPath)
	shutil.copytree(oriDir,tagPath+'/com')
	shutil.copy(pom_path,tmp)
	
# =====================================================================
jar_init('D:/gitCode/Demos/python/python36/protoc/com','D:/gitCode/Demos/python/python36/protoc/pom.xml','11')