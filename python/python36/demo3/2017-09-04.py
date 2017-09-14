#!/user/bin/env python3
# -*- coding: utf-8 -*-
import json
import os
import random
# 进程的试炼
import time
import unittest
from multiprocessing import Process
from multiprocessing.pool import Pool

from util import DateUtil
from util import ProcessUtil


# unittest.skip(reason)	强制跳转。reason是跳转原因
# unittest.skipIf(condition, reason)	条件跳转，如果condition是True则跳转
# unittest.skipUnless(condition, reason)	除非condition为True，才进行调整
# unittest.expectedFailure()	标记该测试预期为失败 ，如果该测试方法运行失败，则该测试不算做失败

class FatherClass(object):
    def normal_method(self):
        pass


class ConflictClass(FatherClass):
    def normal_method(self):
        pass


class SonClass(FatherClass):
    def normal_method(self):
        print('I\'m a %s', self.__class__.__name__)


class SisterClass(FatherClass):
    def normal_method(self):
        print('I\'m a %s', self.__class__.__name__)


class ProcessTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    @staticmethod
    def run_proc(name):
        print('Run child process %s (%s)...' % (name, os.getpid()))

    # 创建一个进程
    @unittest.skip('创建一个进程 case')
    def test_01(self):
        print('Parent process %s.' % (os.getpid()))
        p = Process(target=ProcessTest.run_proc, args=('child test',))
        print('Child process will start.')
        p.start()
        p.join()
        print('Child process end.')

    @staticmethod
    def long_time_task(name):
        print('Run task %s (%s)...' % (name, os.getpid()))
        start = time.time()
        time.sleep(random.random() * 3)
        end = time.time()
        print('Task %s runs %0.2f second.' % (name, (end - start)))

    # 创建一个进程池
    @unittest.skip('创建一个进程 case')
    def test_02(self):
        print('Parent process %s.' % os.getpid())
        p = Pool(4)
        for i in range(5):
            p.apply_async(self.long_time_task, args=(i,))
        print('Waiting for all sub processes done...')
        p.close()
        p.join()
        print('All sub processes done.')

    # 测试python的能否实现 外观模式
    @unittest.skip(' 测试python的能否实现 外观模式')
    def test_03(self):
        person = []
        sister = SisterClass()
        son = SonClass()
        test = ConflictClass()
        person.append(sister)
        person.append(son)
        person.append(test)
        for p in person:
            if isinstance(p, FatherClass):
                p.normal_method()
            else:
                print('%s must extend FatherClass' % p.__class__.__name__)

    def test_04(self):
        list = []
        for i in range(100):
            user_id = i + 1
            access_token = DateUtil.DateParser.parse_stamp_second(DateUtil.DateParser.now()) + user_id + random.randint(
                10, 99)
            list.append(DemoTest(user_id, access_token))
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
