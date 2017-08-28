#!/user/bin/env python3
# -*- coding: utf-8 -*-
#--------------  case  -----------------------
# import time,threading
# def loop():
# 	print('thread %s is running...' % threading.current_thread().name)
# 	n=0
# 	while n < 5:
# 		n=n+1
# 		print('thread %s >>> %s' % (threading.current_thread().name,n))
# 		time.sleep(1)
# 	print('thread %s ended' % threading.current_thread().name)

# print('thread %s is running...' % threading.current_thread().name)
# t=threading.Thread(target=loop,name='LoopThread')
# t.start()
# t.join()
# print('thread %s ended ' % threading.current_thread().name)
#--------------  case  -----------------------
# import time,threading

# balance=0

# def change_it(n):
# 	global balance
# 	balance+=n
# 	balance-=n

# def run_thread(n):
# 	for i in range(100000):
# 		change_it(n)

# t1=threading.Thread(target=run_thread,args=(5,))
# t2=threading.Thread(target=run_thread,args=(8,))
# t1.start()
# t2.start()
# t1.join()
# t2.join()
# print(balance)

#--------------  case  -----------------------
# import time,threading
# balance=0

# def change_it(n):
# 	global balance
# 	balance+=n
# 	balance-=n


# lock=threading.Lock()
# def run_thread(n):
# 	for i in range(1000000):
# 		lock.acquire()
# 		try:
# 			change_it(n)
# 		finally:
# 			lock.release()

# t1=threading.Thread(target=run_thread,args=(5,))
# t2=threading.Thread(target=run_thread,args=(8,))
# t1.start()
# t2.start()
# t1.join()
# t2.join()
# print(balance)
#--------------  case  -----------------------
# import threading
# local_school=threading.local()

# def process_student():
# 	std=local_school.student
# 	print('hello,%s (in %s)' % (std,threading.current_thread().name))

# def process_thread(name):
# 	local_school.student=name
# 	process_student()

# t1=threading.Thread(target=process_thread,args=('Alice',),name='Thread-A')	
# t2=threading.Thread(target=process_thread,args=('Bob',),name='Thread-B')	
# t1.start()
# t2.start()
# t1.join()
# t2.join()

#--------------  case  -----------------------
# import re
# a1=r'www@133.com'
# a2=r'asd@qwe.qwe.qwe'
# a3=r'w..1.q'

# r1=re.match(r'\w+@\w+\.\w+',a1)
# r2=re.match(r'\w+@\w+\.\w+',a2)
# r3=re.match(r'\w+@\w+\.\w+',a3)
# print(r1,'  ',a1)
# print(r2,'  ',a2)
# print(r3,'  ',a3)
#--------------  case  -----------------------
# from datetime import datetime
# now=datetime.now()
# print(now)
# print(type(now))
#--------------  case  -----------------------
# from datetime import datetime
# dt=datetime(2015,4,19,12,20)
# ts=dt.timestamp()
# st=datetime.fromtimestamp(ts)
# uc=datetime.utcfromtimestamp(ts)
# print(dt)
# print(ts)
# print(st)
# print(uc)
#--------------  case  -----------------------
# from datetime import datetime
# now=datetime.now()
# s=now.strftime('%Y-%m-%d %H:%M:%S')
# cday=datetime.strptime(s,'%Y-%m-%d %H:%M:%S')
# print(s)
# print(cday)
#--------------  case  -----------------------
# from datetime import datetime,timedelta
# now=datetime.now()
# t1=now+timedelta(hours=10)
# t2=now-timedelta(days=2)
# t3=now+timedelta(days=1,hours=12)
# print(now)
# print(t1)
# print(t2)
# print(t3)
#--------------  case  -----------------------
# from collections import namedtuple
# Point=namedtuple('Point',['x','y'])
# p=Point(13.21340523,14.12312312)
# print(p.x)
# print(p.y)

#--------------  case  -----------------------
# from collections import deque
# q=deque(['x','y','z'])
# q.append('a')
# print(q)
# q.appendleft('b')
# print(q)
# q.pop()
# print(q)
# q.popleft()
# print(q)

#--------------  case  -----------------------
# from collections import defaultdict
# dd=defaultdict(lambda:'N/A')
# dd['asd']='ff'
# aa={'dd':'12313','asd1':'gasd'}
# print(dd['asd'])
# print(dd['11'])
# print(aa['asd1'])
# print(aa['11'])
#--------------  case  -----------------------
# from collections import OrderedDict
# d=dict([('a',1),('b',2),('c',3)])
# print(d)
# od=OrderedDict([('a',1),('b',2),('c',3)])
# print(od)
#--------------  case  -----------------------
# import base64
# s=r'asdasdasdq\asfa\\\acasdq';
# enc=base64.b64encode(s.encode(encoding='utf-8'))
# print(enc)
# dstr=base64.b64decode(enc)
# print(dstr)
# s1=b'i\xb7\x1d\xfb\xef\xff'
# a1=base64.urlsafe_b64encode(s1)
# a2=base64.urlsafe_b64decode(a1)
# print(a1)
# print(a2)
#--------------  case  -----------------------
# import hashlib
# md5=hashlib.md5()
# md5.update('how to use md5 in python hashlib?'.encode('utf-8'))
# print(md5.hexdigest())

# sha1=hashlib.sha1()
# sha1.update('how to use sha1 in '.encode('utf-8'))
# sha1.update('python hashlib?'.encode('utf-8'))
# print(sha1.hexdigest())
#--------------  case  -----------------------
# import itertools
# natuals=itertools.count(1)
# for n in natuals:
# 	print(n)
#--------------  case  -----------------------
# import itertools
# cs=itertools.cycle('abcdefg')
# for  c in cs:
# 	print(c)
#--------------  case  -----------------------
# import itertools
# ns=itertools.repeat('asd',100)
# for n in ns :
# 	print(n)
#--------------  case  -----------------------
# class Query(object):

#     def __init__(self, name):
#         self.name = name

#     def __enter__(self):
#         print('Begin')
#         return self

#     def __exit__(self, exc_type, exc_value, traceback):
#         if exc_type:
#             print('Error')
#         else:
#             print('End')

#     def query(self):
#         print('Query info about %s...' % self.name)

# with Query('Bob') as q:
#     q.query()

#--------------  case  -----------------------
# from contextlib import contextmanager

# class Query(object):

#     def __init__(self, name):
#         self.name = name

#     def query(self):
#         print('Query info about %s...' % self.name)

# @contextmanager
# def create_query(name):
# 	print('begin')
# 	q=Query(name)
# 	yield q
# 	print('end')


# with create_query('Bob') as q:
# 	q.query()

#--------------  case  -----------------------
# from contextlib import closing
# from urllib.request import urlopen

# with closing(urlopen('https://www.python.org')) as page:
#     for line in page:
#         print(line)
#--------------  case  -----------------------
# from urllib import request

# with request.urlopen('http://blog.csdn.net/boneix/article/details/73608573') as f:
# 	data = f.read()
# 	print('Status: %s and Reason: %s' % (f.status,f.reason))
# 	for k,v in f.getheaders():
# 		print('%s : %s ' % (k,v))
# 	print('Data: %s' % (data.decode('utf-8'),))
#--------------  case  -----------------------
# from urllib import request
# class HttpResult(object):
# 	def __init__(self,status,reason,headers,data):
# 		self.status=status
# 		self.reason=reason
# 		self.headers=headers
# 		self.data=data

# class HttpClient(object):
# 	def __init__(self,url,method,headers={},data=None):
# 		self.url=url
# 		self.method=method
# 		self.headers=headers
# 		self.data=data

# 	def execute(self):
# 		if self.method.lower()=='get':
# 			if(None!=self.data):
# 				self.url=self.url+'?'+self.data
# 			return self.get_data_by_url()

# 	def get_data_by_url(self):
# 		req = request.Request(self.url, headers=self.headers)
# 		with request.urlopen(req) as f:
# 			return HttpResult(f.status,f.reason,f.getheaders(),f.read().decode('utf-8'))
from SimpleHttpClient import *
import json
def get_user_info():
	ip=r'192.168.1.62:8280'
	ip1=r'localhost:8080'
	url=r'http://%s/bk/api/fortress/v1/user/get_user_info' % (ip1,)
	method='GET'
	userId='9'
	projectId='1'
	data = {'userId': '9'}	
	ticket=get_ticket()
	if(None!=ticket):
		headers={
			'Ticket':ticket,
			'User-Id':userId,
			'Project-Id':projectId
		}
		hc=SimpleHttpClient(url,method,headers,data)
		hs=hc.execute() 
		if(hs.reason==True and hs.status==200):
			print(hs.data)
		else:
			print('http request failure, %s' % hs.message)		
	else:
		print('获取ticket失败')

def get_ticket():
	ip=r'192.168.1.62:8280'
	ip1=r'localhost:8080'
	url=r'http://%s/bk/api/fortress/v1/user/login_by_form' % (ip1,)
	method='POST'
	projectId='1'
	headers={
		'Content-Type':'application/json',
		'Project-Id':projectId
		}
	data=r'{"username":"张嵘","password":"mo92017"}'
	hc=SimpleHttpClient(url,method,headers,data)
	hs=hc.execute()
	if(hs.reason==True and hs.status==200):
		return hs.data['data']['entity']['token']
	else:
		return None
# get_ticket()
get_user_info()
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
