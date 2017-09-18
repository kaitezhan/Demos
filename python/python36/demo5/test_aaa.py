#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/18 18:17
# @Author  : boneix

import json
import os

from demo5.data_class import PropertyData
from demo5.file_opt import FileOpt


def aaa():
    pd = PropertyData(None, "/index.html", "GET", "aaa", False, "aaa")
    pd2 = PropertyData(None, "/index2.html", "GET", "aaa", True, "aaa")
    lit = [pd, pd2]
    return json.dumps(lit, default=PropertyData.data2dict)


def bbb():
    lit = json.loads(aaa())
    for x in lit:
        print(x)
        print(type(x))
        data = PropertyData.dict2data(x)
        print(data.code)


def ccc():
    path = os.path.join(os.getcwd(), 'data.cfg')
    FileOpt.write_data_from_file(path, bytes(aaa(), encoding="utf8"))


ccc()
