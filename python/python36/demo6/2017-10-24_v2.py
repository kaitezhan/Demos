#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/24 17:20
# @Author  : boneix
# @Description :first pip3 install pyrestful


import pyrestful.rest
import tornado.ioloop
from pyrestful import mediatypes
from pyrestful.rest import get, post


class Book(object):
    isbn = int
    title = str


class BookResource(pyrestful.rest.RestHandler):
    @get(_path="/books/json/{isbn}", _types=[int], _produces=mediatypes.APPLICATION_JSON)
    def getBookJSON(self, isbn):
        book = Book()
        book.isbn = isbn
        book.title = "My book for isbn " + str(isbn)

        return book

    @get(_path="/books/xml/{isbn}", _types=[int], _produces=mediatypes.APPLICATION_XML)
    def getBookXML(self, isbn):
        book = Book()
        book.isbn = isbn
        book.title = "My book for isbn " + str(isbn)

        return book

    @post(_path="/books/xml", _types=[Book], _consumes=mediatypes.APPLICATION_XML, _produces=mediatypes.APPLICATION_XML)
    def postBookXML(self, book):
        """ this is an echo...returns the same xml document """
        return book

    @post(_path="/books/json", _types=[Book], _consumes=mediatypes.APPLICATION_JSON,
          _produces=mediatypes.APPLICATION_JSON)
    def postBookJSON(self, book):
        """ this is an echo...returns the same json document """
        return book

    @post(_path="/books", _types=[Book])
    def postBook(self, book):
        """ this is an echo, returns json or xml depending of request content-type """
        return book


if __name__ == '__main__':
    try:
        print("Start the service")
        app = pyrestful.rest.RestService([BookResource])
        app.listen(8080)
        tornado.ioloop.IOLoop.instance().start()
    except KeyboardInterrupt:
        print("\nStop the service")
