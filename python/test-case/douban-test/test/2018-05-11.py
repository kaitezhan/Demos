#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/5/11 15:27
# @Author  : boneix
import json
import unittest

from util import ProcessUtil
from util.HttpUtil import SimpleHttpClient


class ProcessTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def test_04(self):
        list = []
        # 构造任务列
        for i in range(100):
            list.append(DemoTest("444201931754176512", "glutton_multisig","ETH"))
        # 执行任务列
        result = ProcessUtil.TaskProcess(3).execute_task(list)
        print('All sub processes done.')
        print(json.dumps(result))


class DemoTest(ProcessUtil.TaskAction):
    def __init__(self, userCode, systemCode, coinUnit):
        self.userCode = userCode
        self.systemCode = systemCode
        self.coinUnit = coinUnit

    def do_in_action(self):
        params = {'userCode': self.userCode, 'systemCode': self.systemCode, 'coinUnit': self.coinUnit}

        url = "https://entrusts.alpha.mo9.com/owlApi/api/owl/v1/wallet/query_multi_signature_account"
        method = 'POST'
        headers = {
            'Content-Type': 'application/json'
        }
        print(url)
        print(params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
            return hs.data
        else:
            print('http request failure, %s' % hs.message)
        return None


if __name__ == '__main__':
    unittest.main()
