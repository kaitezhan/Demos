#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# print ('爱读书')
# name=input("输入一个数字：")
# print (name)
# print(10/3)
# print(10//3)
# a='ccc'
# b=b'ccc'
# print(a)
# print(b)
# aa='ABC'.encode('ascii')
# print(aa);
# bb='中文'.encode('utf-8')
# print(bb);
# L = [x * x for x in range(10)]
# print(L) 
# g = (x * x for x in range(10))
# for n in g:
# print(n) 
# =====================================================================
# 伪生成器-真递归
# def triList(n):
	# L=[1]
	# if n>1:
		# cL=triList(n-1)			
		# for i,d in enumerate(cL):
			# if i!=len(cL)-1:
				# L.append(d+cL[i+1])
		# L.append(1)
	# return L

# def triangles(n):
	# i=1
	# while i<=n:
		# yield triList(i)
		# i+=1
	# return 'done'
# =====================================================================
# 生成器
# def triangles(n):
	# L=[1]
	# i=1
	# while i<=n:
		# if i>1:
			# t=[L[x]+L[x+1] for x in range(i) if x+1<len(L)]
			# L=[1]
			# for d in t:
				# L.append(d)				
			# L.append(1)
		# yield L
		# i+=1	 
	# return 'done'
# =====================================================================
# 执行
# x = triangles(10)
# for g in x:
	# print(g)
# =====================================================================
# from collections import Iterator
# isinstance((x for a in range (10) if a%2==1),Iterator)
# =====================================================================
# 利用map()函数，把用户输入的不规范的英文名字，变为首字母大写，其他小写的规范名字。输入：['adam', 'LISA', 'barT']，输出：['Adam', 'Lisa', 'Bart']：
# def formatStr(x):
	# return x[:1].upper()+x[1:].lower();
# a=list(map(formatStr,['adam', 'LISA', 'barT']))	
# print(a);
# =====================================================================
# Python提供的sum()函数可以接受一个list并求和，请编写一个prod()函数，可以接受一个list并利用reduce()求积：
# from functools import reduce
# def prod(x,y):
	# return x*y;
# aa=reduce(prod,range(1,10));
# print(aa)
# =====================================================================
# 利用map和reduce编写一个str2float函数，把字符串'123.456'转换成浮点数123.456：
# from functools import reduce
# def str2float(s):
	# def fn(x,y):
		# return x*10+y
	# def fn2(x,y):
		# return x/10+y
	# def char2num(bt):
		# dic={'0':0,'1':1,'2':2,'3':3,'4':4,'5':5,'6':6,'7':7,'8':8,'9':9}
		# return dic[bt]
	# sp="."
	# if(sp in s):
		# nPos=s.index(sp)
		# seP=0
		# frP=0
		# fs=s[:nPos]
		# se=s[nPos+1:][::-1]
		# if len(fs)>0:
			# frP=reduce(fn,map(char2num,fs))	
		# if len(se)>0:
			# seP=reduce(fn2,map(char2num,se))	
		# return frP+seP/10;
	# else:
		# return reduce(fn,map(char2num,s))
# print(str2float('3553333.'))
# =====================================================================
# 回数是指从左向右读和从右向左读都是一样的数，例如12321，909。请利用filter()滤掉非回数：
# def is_palindrome(n):
	# x=str(n)
	# l=len(x)
	# c=int(l/2)
	# if l>1:
		# if l%2==1:
			# return x[c+1:][::-1]==x[:c]
		# if l%2==0:
			# return x[c:][::-1]==x[:c]
	# else:
		# return False
# output = filter(is_palindrome, range(1, 1000))
# print(list(output))
# output = filter(lambda n:str(n)[::-1]==str(n), range(1, 1000))

# =====================================================================
# =====================================================================
# =====================================================================
# =====================================================================