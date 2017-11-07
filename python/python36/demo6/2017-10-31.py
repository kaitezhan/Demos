#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/31 17:59
# @Author  : boneix

# !/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import sys

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)
import requests

headers = {'User-Agent':
               'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36'}


def login(afterURL):
    s = requests.session()
    # login = s.post(loginURL, data = par1, headers = headers,verify=False)# 发送登录信息，返回响应信息（包含cookie）
    response = s.get(afterURL, headers=headers, verify=False)  # 获得登陆后的响应信息，使用之前的cookie
    return response.content


# par1 = {"form_email":"13120502501@163.com","password":"Zcl19880509"}
afterURL = "http://192.168.1.53/docs/project/mule.v1.raml.html#provider_find_top_providers_get"  # 想要爬取的登录后的页面


# loginURL = "https://www.douban.com/accounts/login?source=main"     # POST发送到的网址

def pachong():
    # reg = r'<code>\{\s(.*)\s\}</code>'  # 匹配图片地市的方法
    # reg = r'<code>\{\s(.*)\s\}</code>'  # 匹配图片地市的方法
    # reg = r'<code>\{(\s(.*)\s)+\}</code>'  # 匹配图片地市的方法
    # imgre = re.compile(reg)
    t = bytes.decode(login(afterURL))
    # print(t)
    b = []
    s = t.split('<')
    for x in s:
        if 'code>{\n' in x:

            b.append(x[7:])
    print(b)


# print
# print()
# login(afterURL)
pachong()
