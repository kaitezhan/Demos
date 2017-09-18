#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/18 17:06
# @Author  : boneix
import json

from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser


class PropertyData(object):
    def __init__(self, code=None, path="", method=[], head={}, status=True, remark=""):
        self.path = path
        self.method = method
        self.head = head
        self.status = status
        self.remark = remark

        if code is None:
            code = self._create_code_()
        self.code = code

    def _create_code_(self):
        data = {'path': self.path, 'method': self.method, 'time': DateParser.parse_stamp(DateParser.now())}
        return EncryptParser.sha3_256_str(json.dumps(data))

    @staticmethod
    def data2dict(data):
        return {
            'code': data.code,
            'path': data.path,
            'method': data.method,
            'head': data.head,
            'status': data.status,
            'remark': data.remark
        }

    @staticmethod
    def dict2data(dict_str):
        return PropertyData(dict_str['code'], dict_str['path'], dict_str['method'], dict_str['head'],
                            dict_str['status'], dict_str['remark'])
