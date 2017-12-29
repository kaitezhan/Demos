#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/20 18:23
# @Author  : boneix

# unittest.skip(reason)	强制跳转。reason是跳转原因
# unittest.skipIf(condition, reason)	条件跳转，如果condition是True则跳转
# unittest.skipUnless(condition, reason)	除非condition为True，才进行调整
# unittest.expectedFailure()	标记该测试预期为失败 ，如果该测试方法运行失败，则该测试不算做失败
import unittest

from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser
from util.HttpUtil import SimpleHttpClient


class FireFlyFunction(object):
    def __init__(self, base_url, private_key):
        self.base_url = base_url
        self.private_key = private_key

    # 密码登录
    def login_by_password(self, mobile, password):
        params = {'mobile': mobile, 'password': password}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(str(params), timestamp, None)
        headers = {
            'Client-Id': '401',
            'Client-Version': '0',
            'Language': 'cn',
            'Country': 'CN',
            'Content-Type': 'application/json',
            'Device-Id': 'chiputaobutuputaopi',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/auth/login_by_password"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 获取先玩后付银行卡列表
    def query_bank_card_list(self, account_code, access_token):
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign("", timestamp, access_token)
        headers = {
            'Client-Id': '401',
            'Client-Version': '0',
            'Language': 'cn',
            'Country': 'CN',
            'Content-Type': 'application/json',
            'Device-Id': 'chiputaobutuputaopi',
            'Account-Code': account_code,
            'Access-Token': access_token,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/cash/query_bank_card_list"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, None, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 获取先玩后付银行卡列表
    def find_applicable_member_cards(self, account_code, access_token):
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign("", timestamp, access_token)
        headers = {
            'Client-Id': '401',
            'Client-Version': '0',
            'Language': 'cn',
            'Country': 'CN',
            'Content-Type': 'application/json',
            'Device-Id': 'chiputaobutuputaopi',
            'Account-Code': account_code,
            'Access-Token': access_token,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/member/card/find_applicable_member_cards"
        method = 'GET'
        print("===================请求地址：%s" % url)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, None, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 发起会员充值
    def member_recharge(self, account_code, access_token, template_id):
        params = {
            'channelType': 1,
            'bankCard': '6222021108004122255',
            'bankMobile': '15601596366',
            'templateId': template_id,
            'userName': '',
            'idCard': ''
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(params, timestamp, access_token)
        headers = {
            'Client-Id': '401',
            'Client-Version': '0',
            'Language': 'cn',
            'Country': 'CN',
            'Content-Type': 'application/json',
            'Device-Id': 'chiputaobutuputaopi',
            'Account-Code': account_code,
            'Access-Token': access_token,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/cash/member_recharge"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
        else:
            print('===================http request failure, %s' % hs.message)

    # 发起银行卡还款
    def repay(self, account_code, access_token, order_no):
        params = {
            'channelType': 3,
            'bankCard': '6222021108004122255',
            'bankMobile': '15601596366',
            'orderNo': order_no,
            'userName': '',
            'idCard': ''
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(params, timestamp, access_token)
        headers = {
            'Client-Id': '401',
            'Client-Version': '0',
            'Language': 'cn',
            'Country': 'CN',
            'Content-Type': 'application/json',
            'Device-Id': 'chiputaobutuputaopi',
            'Account-Code': account_code,
            'Access-Token': access_token,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/cash/repay"
        method = 'POST'
        hc = SimpleHttpClient(url, method, headers, params, 40)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
        else:
            print('===================http request failure, %s' % hs.message)

    # 生成签名
    def create_sso_sign(self, content, timestamp, access_token):
        str_timestamp = str(timestamp)
        if timestamp % 2 == 0:
            mo9_timestamp = str_timestamp[:10]
        else:
            mo9_timestamp = str_timestamp[-10:]
        print(mo9_timestamp)
        sign_content = str(content).replace('\'', '\"')
        print(sign_content)
        str_sign = sign_content + str(mo9_timestamp) + str(self.private_key)
        if access_token is not None:
            str_sign += str(access_token)
        return EncryptParser.md5_str(str_sign)


class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)
        self.login_check()

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def login_check(self):
        # url = 'https://idnclone.mo9.com/fireflyApi/api/firefly/v1'
        url = 'http://localhost:8082/api/firefly/v1'
        private_key = 'DBD8429F5BEF1C0B50AA7E2CB1EFE65A'
        # mobile = '008615702632794'
        # password = '123'
        mobile = '008615601596366'
        password = '1122334455'
        self.firefly = FireFlyFunction(url, private_key)
        # 登录
        data = self.firefly.login_by_password(mobile, password)
        if data is None:
            print("===================登录返回为null")
            return
        if data['code'] is None:
            print("===================登录返回code为null")
            return
        if data['code'] != 0:
            print("===================登录失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================登录返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================登录返回data.entity为null")
            return
        account_code = data['data']['entity']['accountCode']
        access_token = data['data']['entity']['accessToken']
        if account_code is None or access_token is None:
            print("===================登录返回 data.entity.account_code|access_token 为null")
            return
        self.account_code = account_code
        self.access_token = access_token

    @unittest.skip('has test')
    def test_01(self):
        # 获取银行卡
        self.firefly.query_bank_card_list(self.account_code, self.access_token)

    @unittest.skip('has test')
    def test_02(self):
        # self.account_code = ""
        # self.access_token = ""

        data = self.firefly.find_applicable_member_cards(self.account_code, self.access_token)

        if data is None:
            print("===================登录返回为null")
            return
        if data['code'] is None:
            print("===================登录返回code为null")
            return
        if data['code'] != 0:
            print("===================登录失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================登录返回data为null")
            return
        if data['data']['memberCardTemplates'] is None:
            print("===================登录返回data.memberCardTemplates为null")
            return
        first_template = data['data']['memberCardTemplates'][0]
        if first_template is None:
            print("===================登录返回data.memberCardTemplates[0]为null")
            return
        if first_template['memberCardTemplateId'] is None:
            print("===================登录返回first_template.memberCardTemplateId为null")
            return
        self.firefly.member_recharge(self.account_code, self.access_token, first_template['memberCardTemplateId'])

    def test_03(self):
        order_no = '3128557916029452288'
        self.firefly.repay(self.account_code, self.access_token, order_no)


if __name__ == '__main__':
    unittest.main()
