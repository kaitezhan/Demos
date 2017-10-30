#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/25 10:19
# @Author  : boneix
# from types import MethodType
#
#
# class Student(object):
#     pass
#
#
# def get(self, content):
#     self.aaa = content
#
#
# s = Student()
# s.get = MethodType(get, s)
# s.get("asd")
# print(s.aaa)
import pyrestful
import tornado
from pyrestful import mediatypes
from pyrestful.rest import RestHandler, get


def aaa():
    pass


class RestHandleMetaclass(type):
    @staticmethod
    def dynamicDecorator(data):
        _path = "/books/json/" + str(data) + "/{isbn}"
        _types = [int]
        _produces = mediatypes.APPLICATION_JSON
        # print(data)

        @get(_path=_path, _types=_types, _produces=_produces)
        def getBookJSON(self, isbn):
            book = Book()
            book.isbn = isbn
            book.title = "My book for isbn " + str(isbn)
            return book

        return getBookJSON

    @staticmethod
    def dynamicDecorator2(data):
        _path = "/books/json/" + str(data) + "/{isbn}"
        _types = [int]
        _produces = mediatypes.APPLICATION_JSON
        # print(data)

        @get(_path=_path, _types=_types, _produces=_produces)
        def getBookJSON(self, isbn):
            book = Book()
            book.isbn = isbn
            book.title = "My book for isbn " + str(isbn)
            return book

        return getBookJSON

    def __new__(cls, name, bases, attrs):
        for x in range(5):
            attrs["getBookIsbn_" + str(x)] = RestHandleMetaclass.dynamicDecorator(x)
        return type.__new__(cls, name, (RestHandler,), attrs)


class Book(object):
    isbn = int
    title = str


class TestA(object, metaclass=RestHandleMetaclass):
    pass


if __name__ == '__main__':
    try:
        print("Start the service")
        app = pyrestful.rest.RestService([TestA])
        app.listen(8080)
        tornado.ioloop.IOLoop.instance().start()
    except KeyboardInterrupt:
        print("\nStop the service")
