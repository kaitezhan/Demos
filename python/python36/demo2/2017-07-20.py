#!/user/bin/env python3
# -*- coding: utf-8 -*-
#-------------- tuple case 1  -----------------------
# classmate=('aaa','bbb','cccc')
# t=(1)
# a=(1,)
# print(classmate)
# print(t)
# print(a)

#-------------- tuple case 2 -----------------------
# t=('a','b',['A','B'])
# print(t)
# t[2][0]='x'
# t[2][1]='y'
# print(t)
#-------------- list case 3 -----------------------
# L = [
#     ['Apple', 'Google', 'Microsoft'],
#     ['Java', 'Python', 'Ruby', 'PHP'],
#     ['Adam', 'Bart', 'Lisa']
# ]
# print('Apple is ',L[0][0])
# print('Python is ',L[1][1])
# print('Lisa is ',L[2][2])
#-------------- if case 1  -----------------------
# age=20
# if age>=18:
# 	print('your age is ',age)
# 	print('adult')
#--------------  if case 2 -----------------------
# age=3
# if age>=18:
# 	print('your age is ',age)
# 	print('adult')
# else:
# 	print('your age is ',age)
# 	print('teenager')
#-------------- if case 3 -----------------------
# age = 3
# if age>=18:
# 	print('adult')
# elif age>=6:
# 	print('teenager')
# else:
# 	print('kid')
#-------------- if case 4 -----------------------
# birth=input('birth: ')
# if birth<2000:
# 	print('00前')
# else:
# 	print('00后')
#-------------- if  case 5 -----------------------
# birth=int(input('birth: '))
# if birth<2000:
# 	print('00前')
# else:
# 	print('00后')
#-------------- for case 1 -----------------------
# name=['aa','bb','cc','dd']
# for a in name:
# 	print(a)
#-------------- for case 2 -----------------------
# b=0
# for a in range(101):
# 	b+=a
# print(b)
#-------------- while  case 1 -----------------------
# n=99
# b=0
# while n>0:
# 	b+=n
# 	print(n,b)
# 	n-=2
# print(b)
#-------------- while case 2 -----------------------
# n=1
# while n<100:
# 	if n>80:
# 		break		
# 	n+=1	
# 	if n<30:
# 		continue
# 	print(n)
# print('End')
#-------------- dict case 1 -----------------------
# d={'aaa':80,'bb':82,'sg':71}
# print(d['sg'])
# d['qq']=156
# print(d)
# if 'cc' in d:
# 	print(d['cc'])
# if 'bb' in d:
# 	print(d['bb'])
# print(d.get('cc'))
#-------------- dict case 2 -----------------------
# d={'aaa':80,'bb':82,'sg':71}
# d.pop('bb')
# print(d)
#-------------- set case 1 -----------------------
# s=set([1,2,3,4,5,6,5,4,3,1,1,2,3,4,5,6])
# print(s)
# s.add(7)
# print(s)
# s.remove(3)
# print(s)
# b=set([9,8,7,6,5,4,7,8])
# print(b)
# print(s&b)
# print(s|b)
#-------------- collection case 1 -----------------------
# a=['a','d','f','e']
# a.sort()
# print(a)
# a='abcdefghijklmno'
# print(a)
# print(a.replace('a','A'))
# print(a)

#-------------- function case 1 -----------------------
# print(abs(100))
# print(abs(-100))
# print(max(1,-100,2,34,5,123,5123,-123123))
# print(min(1,-100,2,34,5,123,5123,-123123))
# print(int('123'))
# print(int(123.123123))
# print(float('123.123'))
# print(float('123'))
# print(bool('123'))
# print(bool(1))
# print(bool(''))
# print(bool(None))
# print(bool(' '))
# print(bool('        '.strip()))
#-------------- function case 2 -----------------------
# def my_abs(x):
# 	if x>=0:
# 		return x
# 	else:
# 		return -x

# print(my_abs(-111))		

#-------------- function case 3 -----------------------
# from abstest import my_abs
# print(my_abs(-111))	
# print(my_abs('-111'))	
#-------------- function case 4 -----------------------
# import math
# def move(x,y,step,angle=0):
# 	nx=x+step*math.cos(angle)
# 	ny=y-step*math.sin(angle)
# 	return nx,ny

# x,y=move(10,100,60,math.pi/6)	
# print(x,y)
#-------------- function case 5 -----------------------
# def power3(x):
# 	return x*x*x

# print(power3(4))
# print(power3(5))
#-------------- case  -----------------------
# def powern(x,n):
# 	d=1
# 	while n>0:
# 		n-=1
# 		d*=x
# 		print('n:',n,'d:',d)	
# 	return d
# print(powern(4,3))
# print(powern(5,10))
#-------------- case  -----------------------
# def powern(x,n=2):
# 	d=1
# 	while n>0:
# 		n-=1
# 		d*=x
# 	return d
# print(powern(4))
#-------------- case  -----------------------
# def add_end(L=None):
# 	if L is None:
# 		L=[]
# 	L.append('END')
# 	return L
# print(add_end())
# print(add_end())
# print(add_end())
# print(add_end())
#-------------- case  -----------------------
# def cale(*numbers):
# 	sum=0
# 	for n in numbers:
# 		sum+=(n*n)
# 	return sum

# print(cale(1,2,3,4,5))
# a=[1,3,4,5,2]
# print(cale(*a))
# b=(1,3,4,5,2)
# print(cale(*b))
		
#-------------- case  -----------------------
# def person(name,age,**other):
#     print('name:', name, 'age:', age, 'other:', other)

# person('MMasd',30)
# person('bbq',30,city='asdgg',job='fasfasf')
# extra={'aaa':'asd','bbb':'a4sd','ccc':'asd1','dddd':'a3sd'}
# person('bbq',30,**extra)

#-------------- case  -----------------------
# def person(name,age,*,other,asd):
#     print('name:', name, 'age:', age, 'other:', other,'asd:',asd)

# person('bbq',30,other='asdgg',asd='fasfasf')
#-------------- case  -----------------------
# def person(name, age, *args, city, job):
#     print(name, age, args, city, job)
# extra={'aaa':'asd','bbb':'a4sd','ccc':'asd1','dddd':'a3sd'} 
# person('bbq',30,'aa',job='asdgg',city='fasfasf')
#-------------- 递归 case  -----------------------
# def fact(n):
# 	if n==1:
# 		return 1;
# 	return n*fact(n-1)
# print(fact(10))
#-------------- case  -----------------------
# def fact(n):
# 	return fact_iter(n,1)
# def fact_iter(n,product):
# 	if num==1:
# 		return product
# 	return fact_iter(num-1,num*product)

#-------------- case  -----------------------
# l=['aa','bb','cc','dddd','eee']
# print(l[2:3])
# print(l[:3])
# print(l[-1:])
# print(l[-4:-1])
# print(l[1::2])
# print(l[:])
#-------------- case  -----------------------
# from collections import Iterable
# print(isinstance('abc',Iterable))
# print(isinstance([1,2,3,4,5,6,7],Iterable))
# print(isinstance(123,Iterable))
#-------------- case  -----------------------
# for i,value in enumerate([1,2,3,4,5,6,7]):
# 	print(i,value)
#-------------- case  -----------------------
# a=[x*x for x in range(1,11)]
# print(a)
#-------------- case  -----------------------
d= {'x': 'A', 'y': 'B', 'z': 'C' }
print([k+'='+v for k,v in d.items()])
#-------------- case  -----------------------
#-------------- case  -----------------------