#!/user/bin/env python3
# -*- coding: utf-8 -*-
import json
# 进程的试炼
import unittest

from util import HttpUtil
from util import ProcessUtil


class ProcessTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def test_04(self):
        list = []
        # 构造任务列
        for x in range(15601535366, 15601539366):
            list.append(DemoTest(x))
        # 执行任务列
        result = ProcessUtil.TaskProcess(3).execute_task(list)
        print('All sub processes done.')
        print(json.dumps(result))


class DemoTest(ProcessUtil.TaskAction):
    def __init__(self, mobile):
        self.mobile = mobile

    def do_in_action(self):
        # url = 'https://bkcln.mo9.com/genieApi/api/genie/v1/coupon/lucky_draw'
        # url = 'http://192.168.1.33:8080/genieApi/api/genie/v1/coupon/lucky_draw'
        url = 'http://localhost:8080/api/genie/v1/coupon/lucky_draw'
        method = 'POST'
        data = {"mobile": self.mobile}
        headers = {
            'Content-Type': "application/json"
        }
        hc = HttpUtil.SimpleHttpClient(url, method, headers, data)
        hs = hc.execute()
        if hs.reason is True and hs.status is 200:
            print(hs.data)
        else:
            print('http request failure, %s,%s' % (hs.status, hs.message))
        return ""


if __name__ == '__main__':
    unittest.main()
