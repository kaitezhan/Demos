#!/user/bin/env python3
# -*- coding: utf-8 -*-
import json
import os
import random
# 进程的试炼
import time
import unittest

from util import DateUtil
from util import ProcessUtil


class ProcessTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def test_04(self):
        list = []
        # 构造任务列
        for i in range(100):
            user_id = i + 1
            access_token = DateUtil.DateParser.parse_stamp_second(DateUtil.DateParser.now()) + user_id + random.randint(
                10, 99)
            list.append(DemoTest(user_id, access_token))
        # 执行任务列
        result = ProcessUtil.TaskProcess(3).execute_task(list)
        print('All sub processes done.')
        print(json.dumps(result))


class DemoTest(ProcessUtil.TaskAction):
    def __init__(self, user_id, access_token):
        self.user_id = user_id
        self.access_token = access_token

    def do_in_action(self):
        second = random.random() * 3
        time.sleep(second)
        print('UserId : %s , AccessToken : %s , (%s) ' % (self.user_id, self.access_token, os.getpid()))
        return second


if __name__ == '__main__':
    unittest.main()
