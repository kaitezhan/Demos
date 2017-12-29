#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/29 14:07
# @Author  : boneix
from util.HttpUtil import SimpleHttpClient


class SpiderHandler(object):

    @staticmethod
    def demo1(url, timeout=None):
        shClient=SimpleHttpClient(url,timeout)
        shClient.execute()




url = 'https://www.whatismybrowser.com/'
handler = SpiderHandler(url)
result = handler.execute()
print(result.data)
