#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/29 14:07
# @Author  : boneix
from util.HttpUtil import SimpleHttpClient


class SpiderHandler(object):
    @staticmethod
    def demo1(url):
        shClient = SimpleHttpClient(url)
        return shClient.execute()


# url = 'https://www.whatismybrowser.com/'
url = 'https://movie.douban.com/explore'
handler = SpiderHandler()
result = handler.demo1(url)
filename = 'douban-movie.html'
with open(filename, 'wb') as f:
    f.write(bytes(result.data, encoding="utf8"))
