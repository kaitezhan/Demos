#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/25 11:02
# @Author  : boneix
import unittest

import redis

from util.DateUtil import DateParser, DateOperator
from util.EncryptionUtil import EncryptParser
from util.HttpUtil import SimpleHttpClient


class LuwakFuction(object):
    def __init__(self, base_url, mobile, private_key):
        self.mobile = mobile
        self.base_url = base_url
        self.private_key = private_key

    # 注册
    def register(self):
        code = '233223'
        password = '1122334455'
        self.set_register_verify_code(self.mobile[4:], code)
        params = {'mobile': self.mobile, 'code': code, 'password': password}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/register"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 密码登录
    def login_by_password(self, password='1122334455'):
        params = {'mobile': self.mobile, 'password': password}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/login_by_password"
        method = 'POST'
        print(params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
            return hs.data
        else:
            print('http request failure, %s' % hs.message)

    # 发送注册验证码
    def send_register_code(self):
        params = {'mobile': self.mobile}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/send_register_code"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 发送登录验证码
    def send_login_code(self):
        params = {'mobile': self.mobile}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/send_login_code"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 发送图形验证码
    def send_graph_code(self):
        params = {'mobile': self.mobile}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/send_graph_code"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 验证码登录
    def login_by_code(self):
        code = '233223'
        self.set_login_verify_code(self.mobile[4:], code)
        params = {'mobile': self.mobile, 'code': code}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/login_by_code"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 生成签名
    def create_sso_sign(self, content, timestamp, access_token):
        str_timestamp = str(timestamp)
        if timestamp % 2 == 0:
            mo9_timestamp = str_timestamp[:10]
        else:
            mo9_timestamp = str_timestamp[-10:]
        sign_content = str(content).replace('\'', '\"')
        # print(sign_content, mo9_timestamp, self.private_key, access_token)
        str_sign = sign_content + str(mo9_timestamp) + str(self.private_key)
        if access_token is not None:
            str_sign += str(access_token)
        # print(str_sign)
        return EncryptParser.md5_str(str_sign)

    @staticmethod
    def set_register_verify_code(mobile, code):
        r = redis.Redis(host='118.31.42.204', port=6379, db=20, password="dev@Mo9.com")
        redis_key = 'nest_user_register_sms_validate_code_1.0_hash'
        r.hset(redis_key, mobile, code)
        LuwakFuction._manage_expire_redis_key(redis_key, mobile)

    @staticmethod
    def set_login_verify_code(mobile, code):
        r = redis.Redis(host='118.31.42.204', port=6379, db=20, password="dev@Mo9.com")
        redis_key = 'nest_user_login_sms_validate_code_1.0_hash'
        r.hset(redis_key, mobile, code)
        LuwakFuction._manage_expire_redis_key(redis_key, mobile)

    @staticmethod
    def _manage_expire_redis_key(key, mobile):
        r = redis.Redis(host='118.31.42.204', port=6379, db=20, password="dev@Mo9.com")
        expire_redis_key = 'nest_expire_manager_1.0_permanent'
        count_redis_key = 'nest_user_sms_count_1.0_hash'
        r.hset(expire_redis_key, key + "@" + mobile, DateParser.parse_stamp(DateOperator.add_days(DateParser.now(), 1)))
        r.hset(count_redis_key, mobile, "0")


class LuwakTest(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    @unittest.skip('has test')
    def test_send_register_code(self):
        base_url = 'http://192.168.6.79:8082/api/luwak/v1'
        mobile = "008615601596311"
        private_key = "DBD8429F5BEF1C0B50AA7E2CB1EFE65A"
        lf = LuwakFuction(base_url, mobile, private_key)
        code = "1234567"
        lf.set_register_verify_code(mobile[4:], code)

    @unittest.skip('has test')
    def test_send_login_code(self):
        base_url = 'http://192.168.6.79:8082/api/luwak/v1'
        mobile = "008615601596368"
        private_key = "DBD8429F5BEF1C0B50AA7E2CB1EFE65A"
        lf = LuwakFuction(base_url, mobile, private_key)
        code = "1234567"
        lf.set_login_verify_code(mobile[4:], code)


if __name__ == '__main__':
    unittest.main()
