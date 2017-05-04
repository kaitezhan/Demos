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

# =====================================================================
# =====================================================================
# =====================================================================

	
	
	
	
