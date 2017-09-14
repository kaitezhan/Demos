#!/user/bin/env python3
# -*- coding: utf-8 -*-
import unittest


# unittest.skip(reason)	强制跳转。reason是跳转原因
# unittest.skipIf(condition, reason)	条件跳转，如果condition是True则跳转
# unittest.skipUnless(condition, reason)	除非condition为True，才进行调整
# unittest.expectedFailure()	标记该测试预期为失败 ，如果该测试方法运行失败，则该测试不算做失败

class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    @unittest.skip("this is mast failure")
    def test_01(self):
        self.assertEqual('111', '121')
        print('%s is testing... ' % self._testMethodName)

    def test_02(self):
        self.assertEqual('111', '111')
        print('%s is testing... ' % self._testMethodName)


if __name__ == '__main__':
    unittest.main()
    # unittest.main(defaultTest="DemoTest")

