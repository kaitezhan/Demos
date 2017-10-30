#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/19 15:28
# @Author  : boneix
from util import ProcessUtil, HttpUtil


class ChildTask(ProcessUtil.TaskAction):
    def __init__(self, mobile):
        self.mobile = mobile

    def do_in_action(self):
        url = 'https://bkcln.mo9.com/genieApi/api/genie/v1/coupon/lucky_draw'
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


class CaseTest(object):
    def test_01(self):
        list = []
        for x in range(15601596366, 15601586366):
            print(x)
            list.append(ChildTask(x))
        result = ProcessUtil.TaskProcess(5).execute_task(list)
        print('All sub processes done.')

if __name__ == '__main__':
    for x in range(15601596366, 15601586366):
        print(x)

