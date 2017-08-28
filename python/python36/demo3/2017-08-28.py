#!/user/bin/env python3
# -*- coding: utf-8 -*-

from util import HttpUtil, DateUtil


def modify_user_info():
    # ip = r'192.168.1.62:8280'
    # ip1 = 'localhost:8080'
    #  url = 'http://192.168.1.62:8080/muleApi/api/mule/v1/user/modify_personal_info'
    url = 'http://localhost:8080/api/mule/v1/user/modify_personal_info'
    method = 'POST'
    access_token = '2c7805d64d2e84fc14e2cd8bca91e0d8d909774d'
    user_id = '78'
    data = {'birthday': get_timestamp()}
    headers = {
        'Access-Token': access_token,
        'Current-User-Id': user_id,
        'Content-Type': "application/json"
    }
    hc = HttpUtil.SimpleHttpClient(url, method, headers, data)
    hs = hc.execute()
    if hs.reason is True and hs.status is 200:
        print(hs.data)
    else:
        print('http request failure, %s,%s' % (hs.status, hs.message))


def get_timestamp():
    date_str = "1988-05-08 10:11:22"
    return DateUtil.DateParser.parse_stamp(date_str)


def test_html():
    # ip = r'192.168.1.62:8280'
    # ip1 = 'localhost:8080'
    #  url = 'http://192.168.1.62:8080/muleApi/api/mule/v1/user/modify_personal_info'
    url = 'http://www.baidu.com'
    hc = HttpUtil.SimpleHttpClient(url)
    hs = hc.execute()
    if hs.reason is True and hs.status is 200:
        print(hs.data)
    else:
        print('http request failure, %s,%s' % (hs.status, hs.message))


modify_user_info()
# test_html()
