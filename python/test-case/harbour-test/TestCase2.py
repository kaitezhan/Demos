#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/20 18:23
# @Author  : boneix
import json
import os
import time
# unittest.skip(reason)	强制跳转。reason是跳转原因
# unittest.skipIf(condition, reason)	条件跳转，如果condition是True则跳转
# unittest.skipUnless(condition, reason)	除非condition为True，才进行调整
# unittest.expectedFailure()	标记该测试预期为失败 ，如果该测试方法运行失败，则该测试不算做失败
import unittest

from util.HttpUtil import SimpleHttpClient


class HarbourCheckAmount(object):
    # 对比BTC金额数据
    @staticmethod
    def check_btc_amount(address, amount):
        url = 'https://api.omniexplorer.info/v1/address/addr'
        headers = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
        method = 'POST'
        params = {
            'addr': address
        }
        # print("===================请求地址：%s" % url)
        # print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            # print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                print("===================返回结果 data为null")
                return
            if hs.data['balance'] is None:
                print("===================返回结果 data[balance] 为null")
                return
            if type(hs.data['balance']) is not list:
                print("===================返回结果 data[balance] 不为数组")
            for x in hs.data['balance']:
                if x['symbol'] == 'BTC':
                    print("===================返回结果 金额为： %.8f" % round((int(x['value']) / 100000000), 8))
                    return round((int(x['value']) / 100000000), 8) == amount
            return False
        else:
            print('===================http request failure, %s' % hs.message)
            return False

    # 对比USDT金额数据
    @staticmethod
    def check_usdt_amount(address, amount):
        url = 'https://api.omniexplorer.info/v1/address/addr'
        headers = {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
        method = 'POST'
        params = {
            'addr': address
        }
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                print("===================返回结果 data为null")
                return
            if hs.data['balance'] is None:
                print("===================返回结果 data[balance] 为null")
                return
            if type(hs.data['balance']) is not list:
                print("===================返回结果 data[balance] 不为数组")
            for x in hs.data['balance']:
                if x['symbol'] == 'SP31':
                    print("===================返回结果 金额为： %.8f" % round((int(x['value']) / 100000000), 8))
                    return round((int(x['value']) / 100000000), 8) == amount
            return False
        else:
            print('===================http request failure, %s' % hs.message)
            return False

    # 对比eth金额数据
    @staticmethod
    def check_eth_amount(address, amount):
        url = 'http://192.168.6.32:8080/api/pearl/v1/address/get_balance'
        headers = {}
        method = 'GET'
        params = {
            'address': address,
            'coinType': 'ETH'
        }
        # print("===================请求地址：%s" % url)
        # print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        res = False
        if hs.reason is True and hs.status == 200:
            # print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                print("===================返回结果 data为null")
                return res
            if hs.data['code'] is None:
                print("===================返回结果 code为null")
                return res
            if hs.data['code'] != 0:
                print("===================返回结果 code为 %s" % str(hs.data['code']))
                return res
            if hs.data['data'] is None:
                print("===================返回结果 data为 null")
                return res

            if hs.data['data']['balance'] is None:
                print("===================返回结果 balance 为 null")
                return res
            res = round(hs.data['data']['balance'], 18) == amount
            if res is not True:
                print("===================返回结果 %s 金额为： %.8f   %.8f"
                      % (address, round(hs.data['data']['balance'], 18), amount))
            return res
        else:
            print('===================http request failure, %s' % hs.message)

    # 对比eth金额数据
    @staticmethod
    def check_erc20_amount(address, amount, coin_unit):
        url = 'http://192.168.6.32:8080/api/pearl/v1/address/get_balance'
        headers = {}
        method = 'GET'
        params = {
            'address': address,
            'coinType': coin_unit
        }
        # print("===================请求地址：%s" % url)
        # print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            # print("===================返回结果：%s" % hs.data)
            if hs.data is None:
                # print("===================返回结果 data为null")
                return
            if hs.data['code'] is None:
                # print("===================返回结果 code为null")
                return
            if hs.data['code'] != 0:
                # print("===================返回结果 code为 %s" % str(hs.data['code']))
                return
            if hs.data['data'] is None:
                # print("===================返回结果 data为 null")
                return

            if hs.data['data']['balance'] is None:
                # print("===================返回结果 balance 为 null")
                return

            res = round(hs.data['data']['balance'], 18) == amount
            if res is not True:
                print("===================返回结果 金额为： %.8f   %.8f" % (round(hs.data['data']['balance'], 18), amount))
            return res
        else:
            # print('===================http request failure, %s' % hs.message)
            return False

    @staticmethod
    def read_file_with_json(file_path):
        with open(file_path, 'r') as f:
            return json.loads(f.read().strip().replace('\r', '').replace('\n', '').replace(' ', ''))

    @staticmethod
    def write_file_with_json(file_path, context):
        with open(file_path, 'w') as f:
            return f.write(json.dumps(context))

    @staticmethod
    def write_file_with_line(file_path, context):
        with open(file_path, 'a') as f:
            return f.write(context + '\n')


class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    @unittest.skip('has test')
    def test_check_usdt_amount(self):
        case_list = {
            '1Dfz6fzWZBaqSpcnwvFfih4U3WeqzBem6F': '1.28180298000000000000',
            '1MowAd7svBy7os4ip8FQznsEU9J9f9M1Ss': '1.06085637000000000000',
            '18DARFY8QAMSA12gNDGfnTkBT1hoUJ7JJW': '1.00000000000000000000',
            '1M5CZRuBR7YuPwxHdAupRE3V3RXJW9sJta': '1.00000000000000000000',
            '1DPtY3dEC1DRTBM5CajT2QhsP8ZN2SxW7s': '0.37394786000000000000',
            '16vZt88FMj9zZyoGTmSuxJkH5fes4utpHB': '0.12342366000000000000',
            '16SxpbCRH7WqfUu2tdTp6o51e2HSxz6ydW': '0.01434414000000000000',
            '14VJQtqTU4TSr7HtygSnraVopPvEyffUQW': '0.00902133000000000000',
            '12Sepn3M6Zgb41RxhZsGSqes9QfT6rsYjE': '0.00544328000000000000',
            '15RLwjHdkP2tg1nWqiM9SJMj14Q6vfJoTo': '0.00512579000000000000',
            '1Fs8P1DCLJaGGqEaepoB4pARKxs7dNDRPs': '0.00356992000000000000'
        }

        for key in case_list:
            time.sleep(1)
            res = HarbourCheckAmount.check_usdt_amount(key, float(case_list[key]))
            if res is False:
                print('=================== 金额不对！, %s' % key)

    @unittest.skip('has test')
    def test_check_eth_amount(self):
        path = os.path.join(os.getcwd(), "file", "ETHAccounts.json")
        case_list = HarbourCheckAmount.read_file_with_json(path)
        map_res = {}
        res_path = os.path.join(os.getcwd(), "result", "ETH_result.json")
        for key in case_list:
            res = HarbourCheckAmount.check_eth_amount(key, float(case_list[key]))
            if res is False:
                HarbourCheckAmount.write_file_with_line(res_path, key)

    @unittest.skip('has test')
    def test_check_dai_amount(self):
        path = os.path.join(os.getcwd(), "file", "DAIAccounts.json")
        case_list = HarbourCheckAmount.read_file_with_json(path)

        for key in case_list:
            res = HarbourCheckAmount.check_erc20_amount(key, float(case_list[key]), 'DAI')
            if res is False:
                print('========..........=========== 金额不对！, %s' % key)

    @unittest.skip('has test')
    def test_check_lba_amount(self):
        path = os.path.join(os.getcwd(), "file", "LBAAccounts.json")
        case_list = HarbourCheckAmount.read_file_with_json(path)

        for key in case_list:
            res = HarbourCheckAmount.check_erc20_amount(key, float(case_list[key]), 'LBA')
            if res is False:
                print('========..........=========== 金额不对！, %s' % key)

    @unittest.skip('has test')
    def test_check_ht_amount(self):
        path = os.path.join(os.getcwd(), "file", "HTAccounts.json")
        case_list = HarbourCheckAmount.read_file_with_json(path)

        for key in case_list:
            res = HarbourCheckAmount.check_erc20_amount(key, float(case_list[key]), 'HT')
            if res is False:
                print('========..........=========== 金额不对！, %s' % key)

    @unittest.skip('has test')
    def test_check_bnb_amount(self):
        path = os.path.join(os.getcwd(), "file", "BNBAccounts.json")
        case_list = HarbourCheckAmount.read_file_with_json(path)

        for key in case_list:
            res = HarbourCheckAmount.check_erc20_amount(key, float(case_list[key]), 'BNB')
            if res is False:
                print('========..........=========== 金额不对！, %s' % key)

    @unittest.skip('has test')
    def test_read_file_with_json(self):
        path = os.path.join(os.getcwd(), "file", "ETHAccounts.json")
        print(path)
        json_text = HarbourCheckAmount.read_file_with_json(path)
        print('============ json_text : %s' % json_text)
        print('============ type(json_text) : %s' % type(json_text))

    # @unittest.skip('has test')
    def test_check(self):
        list1 = ['0x74f58ab5910f5653cef4ab11a4965cc843e3003e',
'0x9846d23e8f13c4897fd375d4c2777ff4b5ec2d89']
        list2 = ['0x74f58ab5910f5653cef4ab11a4965cc843e3003e',
'0x9846d23e8f13c4897fd375d4c2777ff4b5ec2d89']

        for x in list1:
            if x not in list2:
                print('=========' + x)


if __name__ == '__main__':
    unittest.main()
