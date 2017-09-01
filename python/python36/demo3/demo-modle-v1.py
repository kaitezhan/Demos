#!/user/bin/env python3
# -*- coding: utf-8 -*-
import unittest


class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def test_01(self):
        self.assertEqual('111', '111')
        print('%s is testing... ' % self._testMethodName)

    def test_02(self):
        self.assertEqual('111', '111')
        print('%s is testing... ' % self._testMethodName)


if __name__ == '__main__':
    unittest.main(defaultTest="DemoTest")
