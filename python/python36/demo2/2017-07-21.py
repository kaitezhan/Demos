#!/user/bin/env python3
# -*- coding: utf-8 -*-
#--------------  case  -----------------------
# L=[x*x for x in range(1,100)]
# print(L)

#--------------  case  -----------------------
# g=(x*x for x in range(1,100))
# print(next(g))
# print(next(g))
# print(next(g))
# print(next(g))
# print(next(g))
# print(next(g))
# print(next(g))
# for n in g:
# 	print(n)
#--------------  case  -----------------------
# def fib(max):
# 	n,a,b=0,0,1
# 	while n<max:
# 		print(b)
# 		a,b=b,a+b
# 		n+=1
# 	return 'done'

# fib(100)	

#--------------  case  -----------------------
# def fib(max):
# 	n,a,b=0,0,1
# 	while n<max:
# 		yield b
# 		a,b=b,a+b
# 		n+=1
# 	return 'done'
# f=fib(6)
# for	n in f:
# 	print(n)
#--------------  case  -----------------------
# def add(x,y,f):
# 	return f(x)+f(y)
# print(add(-5,-6,abs))
#--------------  case  -----------------------
# def f(x):
# 	return x*x
# r=map(f,[1,2,3,4,5,6,7,8,9])
# print(list(r))
#--------------  case  -----------------------
# from functools import reduce
# def add(x,y):
# 	return x+y

# print(reduce(add,[1,2,3,4,5]))	
#--------------  case  -----------------------
# from functools import reduce
# def fn(x,y):
# 	return 10*x+y
# def ft(s):
# 	return list(s)

# print(reduce(fn,map(int,ft('123456181216'))))
#--------------  case  -----------------------
# def is_odd(n):
# 	return n%2==1
# print(list(filter(is_odd,[1,2,3,4,5,6,7,8,9,10])))	
#--------------  case  -----------------------
# def not_empty(s):
# 	return s and s.strip()
# print(list(filter(not_empty,['A','','b',None,'c','   '])))
#--------------  case  -----------------------
# def _old_iter():
# 	n=1
# 	while True:
# 		n+=2
# 		yield n
# def _not_divisible(n):
# 	return lambda x:x%n>0
# def primes():
# 	yield 2
# 	it=_old_iter()
# 	while True:
# 		n=next(it)
# 		yield n
# 		it=filter(_not_divisible(n),it)
# for n in primes():
# 	if n<1000:
# 		print(n)
# 	else:
# 		break

#--------------  case  -----------------------
# def lazy_sum(*args):
# 	def sum():
# 		ax=0;
# 		for n in args:
# 			ax+=n
# 		return ax
# 	return sum
# f=lazy_sum(1,3,5,6,8,9)	
# print(f)
# print(f())
#--------------  case  -----------------------
# def count():
# 	fs=[]
# 	for i in range(1,4):
# 		def f():
# 			return i*i
# 		fs.append(f)
# 	return fs
# f1,f2,f3=count()
# print(f1())
# print(f2())
# print(f3())
#--------------  case  -----------------------
# def now():
# 	print('2015-04-15')
# f=now
# f()
#--------------  case  -----------------------
# def log(func):
# 	def wrapper(*args,**kw):
# 		print('call %s()' % func.__name__)
# 		return func(*args,**kw)
# 	return wrapper
# @log
# def now():
# 	print('2015-04-15')

# now()	
#--------------  case  -----------------------
# def log(text):
# 	def decorator(func):
# 		def wrapper(*args,**kw):
# 			print('%s %s():' % (text,func.__name__))
# 			return func(*args,**kw)
# 		return wrapper
# 	return decorator
# @log('call')
# def now():
# 	print('2015-04-15')	

# now()	
#--------------  case  -----------------------
# improt functools
# def log(func):
# 	@functools.wraps(func)
# 	def wrapper(*args,**kw):
# 		print('%s %s():' % (text,func.__name__))
# 		return func(*args,**kw)
# 	return wrapper

#--------------  case  -----------------------
# import functools
# int2=functools.partial(int,base=2)
# print(int2('100100'))
#--------------  case  -----------------------
# class Student(object):
# 	def __init__(self,name,score):
# 		self.name=name
# 		self.score=score

# 	def print_score(self):
# 		print('%s:%s ' % (self.name,self.score))

# 	def get_grade(self):
# 		if self.score>=90:
# 			return 'A'
# 		elif self.score>=60:
# 			return 'B'
# 		else:
# 			return 'C'

# bart=Student('Bart Simpson',55)
# lisa=Student('Lisa Simpson',87)
# bart.print_score()
# lisa.print_score()
# print(bart.get_grade())
# print(lisa.get_grade())
#--------------  case  -----------------------
# class Animal(object):
# 	def run(self):
# 		print('local class name is %s' % self.__class__)
# class Dog(Animal):
# 	pass
# class Cat(Animal):
# 	pass
# ani1=Animal()
# dog1=Dog()
# cat1=Cat()
# ani1.run()
# dog1.run()
# cat1.run()
#--------------  case  -----------------------
# class Student(object):
# 	pass

# s=Student()
# s.name='Michael'
# print(s.name)

# def set_age(self,age):
# 	self.age=age

# from types import MethodType
# s.set_age=MethodType(set_age,s)
# s.set_age(25)
# print(s.age)
#--------------  case  -----------------------
# class Student(object):
# 	__slots__=('name','age')

# s=Student()
# s.name='Michael'
# s.age=25
# s.score=66
#--------------  case  -----------------------
# class Student(object):
# 	def get_score(self):
# 		return self.__score
# 	def set_score(self,value):
# 		if not isinstance(value,int):
# 			raise ValueError('score must be an Integer!')
# 		if value<0 or value>100:
# 			raise ValueError('score must between 0 and 100 !')
# 		self.__score=value
# s=Student()
# # s.set_score('asd')
# s.set_score(80)
# print(s.get_score())

#--------------  case  -----------------------
# from enum import Enum,unique
# @unique
# class Weekday(Enum):
# 	Sun=0
# 	Mon=1
# 	Tue=2
# 	Wed=3
# 	Thu=4
# 	Fri=5
# 	Sat=6

# day1=Weekday.Mon
# print(day1)
#--------------  case  -----------------------
# class Hello(object):
# 	def hello(self,name='world'):
# 		print('hello,%s ' % name)
# print(type(Hello))
# def fn(self, name='world'):
# 	print('Hello, %s.' % name)
# Sddasf=type('Asd',(object,),dict(hello=fn))

# print(dir(Sddasf))
#--------------  case  -----------------------
class ListMetaclass(type):
	def __new__(cls,name,bases,attrs):
		attrs['add']=lambda self,vale:self.append(vale)
		return type.__new__(cls,name,bases,attrs)

class MyList(list,metaclass=ListMetaclass):
	pass

L=MyList()
L.add(1)
print(L)

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
