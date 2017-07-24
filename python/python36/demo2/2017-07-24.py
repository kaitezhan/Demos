#!/user/bin/env python3
# -*- coding: utf-8 -*-
#--------------  case  -----------------------
# try:
# 	print("try....")
# 	r=10/0
# 	print('result: ',r)
# except ZeroDivisionError as e:
# 	print('exception: ',e)
# finally:
# 	print('finally...')
# print('End')
#--------------  case  -----------------------

# try:
# 	print("try....")
# 	r=10/ int('a')
# 	print('result: ',r)
# except ValueError as e:
# 	print('ValueError:',e)
# except ZeroDivisionError as e:
# 	print('ZeroDivisionError: ',e)
# finally:
# 	print('finally...')
# print('End')
#--------------  case  -----------------------
# import logging
# def foo(s):
# 	return 10/int(s)

# def bar(s):
# 	return foo(s)*2

# def main():
# 	try:
# 		bar('0')
# 	except Exception as e:
# 		logging.exception(e)

# main()
# print('End')
	
#--------------  case  -----------------------
# class Dict(dict):
# 	"""docstring for Dict"""
# 	def __init__(self, **kw):
# 		super().__init__(**kw)
		
# 	def __getattr__(self,key):
# 		try:
# 			return self[key]
# 		except KeyError:
# 			raise AttributeError(r"'Dict' object has no attribute '%s'" % key)

# 	def __setattr__(self,key,value):
# 		self[key]=value

# import unittest

# class TestDict(unittest.TestCase):

# 	def test_init(self):
# 		d=Dict(a=1,b='test')
# 		self.assertEqual(d.a,1)
# 		self.assertEqual(d.b,'test')
# 		self.assertTrue(isinstance(d, dict))

# 	def test_keyerror(self):
# 		d = Dict()
# 		with self.assertRaises(KeyError):
# 			value = d['empty']


# if __name__=='__main__':
	# unittest.main()
#-------------- io case  -----------------------
# try:
# 	f=open('abstest.py','r',encoding = 'utf-8')
# 	print(f.read())
# finally:
# 	if f:
# 		f.close()

#--------------  case  -----------------------
# with open('abstest.py','r',encoding = 'utf-8') as f:
# 	print(f.read())
#--------------  case  -----------------------
# with open('abstest.py','r',encoding = 'utf-8') as f:
# 	for line in f.readlines():
# 		print(line.strip()) #把末尾的'\n'删掉
#--------------  case  -----------------------
# with open('abstest.py','a+',encoding = 'utf-8') as f:
# 	f.write('\nhello ! anybody here ? ')
# 	for line in f.readlines():
# 			print(line.strip()) #把末尾的'\n'删掉
#--------------  case  -----------------------
# from io import StringIO
# f=StringIO()
# f.write('hello')
# f.write('...')
# f.write('anybody here?')
# print(f.getvalue())
#--------------  case  -----------------------
# from io import BytesIO
# f=BytesIO()
# f.write('中文'.encode('utf-8'))
# print(f.getvalue())
#--------------  case  -----------------------
# import os
# print(os.name)
# # print(os.environ)
# print(os.environ.get('JAVA_HOME'))
# print(os.path.abspath('.'))
# t=os.path.abspath('.')
# d=os.path.join(t,'testdir')
# os.mkdir(d)
# os.rmdir(d)

#--------------  case  -----------------------
# import os
# t=os.path.abspath('.')
# d=os.path.join(t,'abstest.py')
# print(d)
# e=os.path.split(d)
# print(e)
# f=os.path.splitext(d)
# print(f)
#--------------  case  -----------------------
# import os
# with open('a.txt','w+') as f:
# 	pass
# os.rename('a.txt','t.ay')
# os.remove('a.txt')
#--------------  case  -----------------------
# import os
# d=[x for x in os.listdir('.') if os.path.isdir(x)]
# print(d)
#--------------  case  -----------------------
# import os
# d=[x for x in os.listdir('.') if os.path.isfile(x) and os.path.splitext(x)[1]=='.py']
# print(d)
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
