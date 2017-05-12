#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'a test module'

# =====================================================================
#import logging
# try:
# 	file = open('c.py','r',encoding='utf-8')
# 	for line in file.readlines():
# 			print(line.strip())
# except Exception as e:
#         logging.exception(e)
# finally:
# 	file.close()
# file = open('bg.jpg','rb')
# try:
	# for line in file.readlines():
			# print(line.strip())
# except Exception as e:
	# logging.exception(e)
# finally:
	# file.close()
# =====================================================================
# from io import StringIO
# # f=StringIO()
# # f.write('hello')
# # f.write('     ')
# # f.write('hello')
# # f.write('     ')
# # print(f.getvalue())

# f=StringIO('I\'ve a little donkey,\nI never rided.someday,\nI ride it to market suddenly')
# while True:
	# s=f.readline()
	# if s=="":
		# break
	# print(s.strip())
# =====================================================================
from io import BytesIO
f=BytesIO()
f.write('爱神的箭'.encode('utf-8'))
print(f.getvalue())

# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================