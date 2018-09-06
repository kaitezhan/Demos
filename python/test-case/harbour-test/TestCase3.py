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


class HarbourFunction(object):
    def __init__(self, base_url, private_key, system_code):
        self.base_url = base_url
        self.private_key = private_key
        self.system_code = system_code

    # 创建账户
    def complete_eth_digital_account(self, address):
        params = {
            'coinUnit': 'ETH',
            'address': address,
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sign(str(params), timestamp)
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': 'Libra-Credit-Cash',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/wallet/complete_eth_digital_account"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                print("===================创建账户返回为null")
                return
            if hs.data['code'] is None:
                print("===================创建账户返回code为null")
                return
            if hs.data['code'] != 0:
                print("===================创建账户失败，%s" % str(hs.data))
                return
            if hs.data['data'] is None:
                print("===================创建账户返回data为null")
                return
            if hs.data['data']['entity'] is None:
                print("===================创建账户返回data.entity为null")
                return

            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 创建账户
    def complete_multi_eth_digital_account(self, address):
        params = {
            'coinUnit': 'ETH',
            'address': address,
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sign(str(params), timestamp)
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': 'Libra-Credit-Collateral',
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/wallet/complete_multi_eth_digital_account"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                print("===================创建账户返回为null")
                return
            if hs.data['code'] is None:
                print("===================创建账户返回code为null")
                return
            if hs.data['code'] != 0:
                print("===================创建账户失败，%s" % str(hs.data))
                return
            if hs.data['data'] is None:
                print("===================创建账户返回data为null")
                return
            if hs.data['data']['entity'] is None:
                print("===================创建账户返回data.entity为null")
                return

            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 生成签名
    def create_sign(self, content, timestamp):
        str_timestamp = str(timestamp)
        if timestamp % 2 == 0:
            mo9_timestamp = str_timestamp[:10]
        else:
            mo9_timestamp = str_timestamp[-10:]
        sign_content = str(content).replace('\'', '\"')
        str_sign = sign_content + str(mo9_timestamp) + str(self.private_key)
        return EncryptParser.md5_str(str_sign)


class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    @unittest.skip('has test')
    def test_create_digital_account(self):
        url = 'https://libra.alpha.mo9.com/pearl-harbourApi/api/harbour/v1'
        # url = 'https://alpha.libracredit.io/pearl-harbourApi/api/harbour/v1'
        private_key = '0A89964F04702698'
        system_code = 'Libra-Credit-Cash'

        self.harbour = HarbourFunction(url, private_key, system_code)
        list1 = ['0xC0E23BA261360BCdb2e9F2DcD9BA57966d66d342']
        for x in list1:
            # 创建账户
            data = self.harbour.complete_eth_digital_account(x)
            print("===================创建账户返回 ，%s" % str(data))

    # @unittest.skip('has test')
    def test_complete_multi_eth_digital_account(self):
        url = 'https://libra.alpha.mo9.com/pearl-harbourApi/api/harbour/v1'
        # url = 'https://alpha.libracredit.io/pearl-harbourApi/api/harbour/v1'
        private_key = '11230e0AF7b8090b'
        system_code = 'Libra-Credit-Collateral'

        self.harbour = HarbourFunction(url, private_key, system_code)
        list1 = ['0x5bf03d2134f6b103f4ff70c554a6ab232cc7c4df']
        for x in list1:
            # 创建账户
            data = self.harbour.complete_multi_eth_digital_account(x)
            print("===================创建账户返回 ，%s" % str(data))


if __name__ == '__main__':
    unittest.main()
