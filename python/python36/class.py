#!/usr/bin/env python3
# -*- coding: utf-8 -*-
'a test module'

# =====================================================================
# class Student(object):
	# def __init__(self,name,score):
		# self.name=name
		# self.score=score
	# def print_score(self):
		# print('%s: %s' %(self.name,self.score))
	# def get_grade(self):
		# if self.score>=90:
			# return 'A'
		# elif self.score>=60:
			# return 'B'
		# else:
			# return 'C'

# bart=Student('Jesscal Noicy',86)
# print(bart.name)
# print(bart.score)
# print('============= I\'m a line ==================')
# bart.print_score()
# print('============= I\'m a line ==================')
# print(bart.get_grade())
# =====================================================================
# class Student(object):
	# def __init__(self,name,score):
		# self.__name=name
		# self.__score=score
	# def print_score(self):
		# print('%s: %s' %(self.__name,self.__score))
	# def get_grade(self):
		# if self.score>=90:
			# return 'A'
		# elif self.score>=60:
			# return 'B'
		# else:
			# return 'C'
	# def get_name(self):
		# return self.__name
	# def get_score(self):
		# return self.__score
	# def set_name(self,name)
		# self.__name=name
	# def set_score(self,score)
		# self.__score=score
		
# bart=Student('Jesscal Noicy',86)
# print(bart.__name)
# =====================================================================
# 继承和多态
import inspect
class Animal(object):
	def run(self):
		print('%s is running...' %(self.__class__.__name__))
class Dog(Animal):
	pass
class Cat(Animal):
	pass
dog =Dog();
dog.run()
cat =Cat();
cat.run()
print('============= I\'m a line ==================')
def do_run(animal):	
	animal.run();
do_run(dog);
do_run(cat);
print('============= I\'m a line ==================')
print(dir(dog))
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