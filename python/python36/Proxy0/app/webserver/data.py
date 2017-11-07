#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/24 19:57
# @Author  : Boneix

import json

from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser


class PropertyData(object):
    def __init__(self, code=None, path="", method=[], date_type="Text", data=None, file=None, head={}, status=True,
                 remark=""):
        self.path = path
        self.method = method
        self.dateType = date_type
        self.data = data
        self.file = file
        self.head = head
        self.status = status
        self.remark = remark

        if code is None:
            code = self._create_code_()
        self.code = code

    def _create_code_(self):
        data = {'path': self.path, 'time': DateParser.parse_stamp(DateParser.now())}
        return EncryptParser.sha3_256_str(json.dumps(data))

    @staticmethod
    def data2dict(data):
        return {
            'code': data.code,
            'path': data.path,
            'method': data.method,
            'dateType': data.dateType,
            'data': data.data,
            'file': data.file,
            'head': data.head,
            'status': data.status,
            'remark': data.remark
        }

    @staticmethod
    def dict2data(dict_str):
        return PropertyData(dict_str['code'], dict_str['path'], dict_str['method'], dict_str['dateType'],
                            dict_str['data'], dict_str['file'], dict_str['head'],
                            dict_str['status'], dict_str['remark'])
