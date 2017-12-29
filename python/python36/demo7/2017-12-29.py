#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/29 10:31
# @Author  : boneix
# ----------------------------------
# from fake_useragent import UserAgent
#
# from util.DateUtil import DateParser
# from util.HttpUtil import SimpleHttpClient


# def http_request(base_url, mobile, password):
#     params = {'mobile': mobile, 'password': password}
#     timestamp = DateParser.parse_stamp(DateParser.now())
#     ua = UserAgent()
#     print(ua.chrome)
#     headers = {
#         'Client-Id': '401',
#         'Client-Version': '0',
#         'Language': 'cn',
#         'Country': 'CN',
#         'Content-Type': 'application/json',
#         'Device-Id': 'chiputaobutuputaopi',
#         'Timestamp': str(timestamp)
#     }
#     url = base_url + "/request_info"
#     method = 'POST'
#     print("===================请求地址：%s" % url)
#     print("===================请求参数：%s" % params)
#     hc = SimpleHttpClient(url, method, headers, params, 10)
#     hs = hc.execute()
#     if hs.reason is True and hs.status == 200:
#         print("===================返回结果：%s" % hs.data)
#         return hs.data
#     else:
#         print('===================http request failure, %s' % hs.message)
#
#
# url = 'http://localhost:8090'
# mobile = '008615601596366'
# password = '1122334455'
# http_request(url, mobile, password)
# ----------------------------------
# from fake_useragent import UserAgent
# from selenium import webdriver
# from selenium.webdriver import DesiredCapabilities
#
# from util.DateUtil import DateParser
#
#
# def http_request(base_url, mobile, password):
#     params = {'mobile': mobile, 'password': password}
#     timestamp = DateParser.parse_stamp(DateParser.now())
#
#     headers = {
#         'Client-Id': '401',
#         'Client-Version': '0',
#         'Language': 'cn',
#         'Country': 'CN',
#         'Content-Type': 'application/json',
#         'Device-Id': 'chiputaobutuputaopi',
#         'Timestamp': str(timestamp)
#     }
#     url = base_url + "/request_info"
#     method = 'POST'
#     print("===================请求地址：%s" % url)
#     print("===================请求参数：%s" % params)
#
#     path = 'D:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs'
#     dcap = dict(DesiredCapabilities.PHANTOMJS)
#     ua = UserAgent()
#     dcap["phantomjs.page.settings.userAgent"] = ua.chrome
#     driver = webdriver.PhantomJS(executable_path=path, desired_capabilities=dcap)
#     driver.get(url)
#     print(driver.page_source)
#     # hc = SimpleHttpClient(url, method, headers, params, 10)
#     # hs = hc.execute()
#     # if hs.reason is True and hs.status == 200:
#     #     print("===================返回结果：%s" % hs.data)
#     #     return hs.data
#     # else:
#     #     print('===================http request failure, %s' % hs.message)
#
#
# url = 'http://localhost:8090'
# mobile = '008615601596366'
# password = '1122334455'
# http_request(url, mobile, password)
# ----------------------------------
# from selenium import webdriver
#
# path = 'D:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs'
# driver = webdriver.PhantomJS(executable_path=path)
# driver.get("https://www.whatismybrowser.com/")
# ----------------------------------
from util.HttpUtil import SimpleHttpClient

url = 'https://www.whatismybrowser.com/'
client = SimpleHttpClient(url, timeout=10)
result = client.execute()
print(result.data)
